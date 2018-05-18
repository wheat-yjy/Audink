package com.jishaokang.nlp.util;

import com.jishaokang.nlp.model.Dialogue;
import com.jishaokang.nlp.model.Pronoun;
import com.jishaokang.nlp.model.Sentence;
import com.jishaokang.util.FileUtil;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import lombok.Data;
import org.springframework.stereotype.Component;


import java.io.*;
import java.util.*;


/**
 * Created by NANA_Final on 2018/1/26.
 */
@Data
@Component
public class ReplacePronouns {



    private String replacedText;

    private Set<String> characters = new HashSet<String>();


    public void replacePronouns(String file,String path,String uploadpath) throws IOException {

        String text = FileUtil.readFile(file);
        System.out.println(text);
        //初始化加载
        ExpressVerbUtil.init(path);

        String[] args = new String[] {"-props", path+"/StanfordCoreNLP-chinese.properties" };
        Annotation document = new Annotation(text);
        Properties props = StringUtils.argsToProperties(args);
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline.annotate(document);

        //分句
        List<Sentence> sentences = new ArrayList<Sentence>();
        List<CoreMap> coreMaps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap coreMap: coreMaps) {
            Sentence sentence = new Sentence();
            sentence.setCoreMap(coreMap);
            sentence.setContent(coreMap.toString());
            sentences.add(sentence);
        }

        //指代消解
        //System.out.println("指代消解-------------------------------------------------------");

        List<Pronoun> pronouns = new ArrayList<Pronoun>();
        Map<Integer, CorefChain> corefChains =  document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
        for (Map.Entry<Integer,CorefChain> entry: corefChains.entrySet()) {

            //System.out.println("chain : "+entry.getKey()+"---------------------------");

            //找出介词对应的人名 而且在人名库
            String originName = null;
            for (CorefChain.CorefMention m : entry.getValue().getMentionsInTextualOrder()) {

                List<CoreLabel> tokens = coreMaps.get(m.sentNum - 1).get(CoreAnnotations.TokensAnnotation.class);
                if (tokens.get(m.endIndex - 2).get(CoreAnnotations.NamedEntityTagAnnotation.class).equals("PERSON")){
                    originName = tokens.get(m.endIndex - 2).value();
                }
                //System.out.println(sentences.get(m.sentNum - 1).getContent());
                //System.out.println("mention : " + text.substring(tokens.get(m.startIndex - 1).beginPosition(),tokens.get(m.endIndex - 2).endPosition()));
                //System.out.print(tokens.get(m.endIndex - 2).value() + " : ");
                //System.out.println(tokens.get(m.endIndex - 2).get(CoreAnnotations.NamedEntityTagAnnotation.class));
            }
            if (originName == null) continue;
            //System.out.println("origin name:"+originName);
            //System.out.println("代词替换结果-----------");
            //找出介词
            for (CorefChain.CorefMention m : entry.getValue().getMentionsInTextualOrder()) {
                List<CoreLabel> tokens = coreMaps.get(m.sentNum - 1).get(CoreAnnotations.TokensAnnotation.class);
                if (tokens.get(m.startIndex - 1).get(CoreAnnotations.PartOfSpeechAnnotation.class).equals("PN")){
                    Pronoun pronoun = new Pronoun(originName,m.endIndex - 2);
                    sentences.get(m.sentNum-1).addPronouns(pronoun);
                    //System.out.println(sentences.get(m.sentNum - 1).getContent());
                    //System.out.println(text.substring(tokens.get(m.startIndex - 1).beginPosition(),tokens.get(m.endIndex - 2).endPosition())+" : "+originName);
                    //System.out.println(pronoun);
                }
            }
        }

        //寻找句子中的主语
        characters.add("她");
        characters.add("他");

        boolean inCharacter;
        String replaceCharacter;
        for (Sentence sentence : sentences){

            //人名库 寻找最大匹配
            List<CoreLabel> tokens = sentence.getCoreMap().get(CoreAnnotations.TokensAnnotation.class);
            for (CoreLabel token : tokens)
                if (token.get(CoreAnnotations.NamedEntityTagAnnotation.class).equals("PERSON")){
                    inCharacter = false;
                    replaceCharacter = null;
                    for (String character : characters){
                        //System.out.println(token.value() + "  *  " + character);
                        if (character.indexOf(token.value()) < 0) {
                            if (token.value().indexOf(character) >= 0) replaceCharacter = character;
                        }
                        else inCharacter = true;
                    }
                    //System.out.println(token.value()+"   "+inCharacter+"   "+replaceCharacter);
                    if (!inCharacter) characters.add(token.value());
                    if  (replaceCharacter != null) characters.remove(replaceCharacter);
                }

            //System.out.println(sentence.getContent());
            Tree tree = sentence.getCoreMap().get(TreeCoreAnnotations.TreeAnnotation.class);
            //System.out.println(tree.pennString());
            SemanticGraph graph = sentence.getCoreMap().get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            //System.out.println(graph.toString(SemanticGraph.OutputFormat.LIST));

            Iterator var2 = graph.edgeListSorted().iterator();
            Dialogue potentialDialogue = null;
            while(var2.hasNext()) {
                SemanticGraphEdge edge = (SemanticGraphEdge)var2.next();
                //System.out.println(edge.getRelation().toString());
                if (QuoteUtil.inQuote(sentence.getContent(),tokens.get(edge.getTarget().index()-1).beginPosition()-tokens.get(0).beginPosition()) && sentence.getDialogue() == null) {
                    if (potentialDialogue == null && edge.getRelation().toString().equals("nsubj")) {
                        potentialDialogue = new Dialogue();
                        potentialDialogue.setName(edge.getTarget().value());
                        potentialDialogue.setTokenIndex(edge.getTarget().index() - 1);
                    }
                    if (findCharacter(edge.getTarget().value())!=null && ExpressVerbUtil.IsExpressVerb(edge.getSource().value())){
                        sentence.addDialogue();
                        sentence.getDialogue().setName(edge.getTarget().value());
                        sentence.getDialogue().setTokenIndex(edge.getTarget().index() - 1);
                        //System.out.println(sentence.getDialogue());
                    }
                }
            }
            sentence.addDialogue();
            if (sentence.getDialogue().getName() == null  &&  potentialDialogue != null){
                sentence.addDialogue();
                sentence.getDialogue().setName(potentialDialogue.getName());
                sentence.getDialogue().setTokenIndex(potentialDialogue.getTokenIndex());
            }
            //System.out.println(sentence.getDialogue());
        }
        //System.out.println("对话-------------------------------------------------------");
        //替换词性为代词的说话者的原名
        for (Sentence sentence : sentences)
            if (sentence.getDialogue() != null && sentence.getPronouns() != null)
                for (Pronoun pronoun : sentence.getPronouns())
                    if (sentence.getDialogue().getTokenIndex() == pronoun.getTokenIndex()){
                        sentence.getDialogue().setName(pronoun.getName());
                        break;
                    }
        //替换原名
        for (Sentence sentence : sentences)
            if (sentence.getDialogue() != null && sentence.getDialogue().getName() != null)
                for (String character : characters){
                    if (character.indexOf(sentence.getDialogue().getName()) >= 0) {
                        sentence.getDialogue().setName(character);
                        break;
                    }
                }

        //判断是否为对话 在引号内 动词为讲话
        boolean openQuote = false;
        int currentDialogueNumber = 0;
        String currentDialogueName = null;
        int currentLength = -1;
        for (Sentence sentence : sentences){
            currentLength++;
            //System.out.println();
            //System.out.println(sentence.getContent());
            if (!openQuote && ExpressVerbUtil.IsExpressVerb(sentence.getContent())){
                currentDialogueName = sentence.getDialogue().getName();
            }
/*            if (!openQuote && ExpressVerbUtil.IsExpressVerb(sentence.getContent()) && currentDialogueNumber > 0){
                for (int i=1; i<=currentDialogueNumber; i++){
                    sentences.get(currentLength-i).getDialogue().setName(sentence.getDialogue().getName());
                }
                currentDialogueNumber = 0;
            }*/
            if (openQuote) {
                sentence.addDialogue();
                sentence.getDialogue().setName(currentDialogueName);
                if (currentDialogueName == null) currentDialogueNumber++; else currentDialogueNumber = 0;
                if (sentence.getContent().indexOf('”') > 0){
                    sentence.getDialogue().setContent(sentence.getContent().substring(0,sentence.getContent().length() - 1));
                    openQuote = false;
                    currentDialogueName = null;
                }
                else {
                    sentence.getDialogue().setContent(sentence.getContent().substring(0, sentence.getContent().length()));
                }
            }
            else {if (sentence.getDialogue() == null) sentence.addDialogue();
                int currentOpenQuotePosition = sentence.getContent().indexOf('“');
                int currentCloseQuotePosition = sentence.getContent().indexOf('”',currentOpenQuotePosition);
                sentence.getDialogue().setContent("");
                while (currentCloseQuotePosition > 0){
                    sentence.getDialogue().setContent(sentence.getDialogue().getContent()+sentence.getContent().substring(currentOpenQuotePosition+1,currentCloseQuotePosition));
                    currentOpenQuotePosition = sentence.getContent().indexOf('“',currentCloseQuotePosition);
                    if (currentOpenQuotePosition < 0) break;
                    currentCloseQuotePosition = sentence.getContent().indexOf('”',currentOpenQuotePosition);
                }
                if (currentOpenQuotePosition >= 0 && currentCloseQuotePosition < 0){
                    openQuote = true;
                    if (sentence.getDialogue().getName()!=null) currentDialogueName = sentence.getDialogue().getName();
                    else sentence.getDialogue().setName(currentDialogueName);
                    sentence.getDialogue().setContent(sentence.getDialogue().getContent()+sentence.getContent().substring(currentOpenQuotePosition+1));
                }
                //if (sentence.getDialogue().getName() == null) currentDialogueNumber++; else currentDialogueNumber = 0;
                if (sentence.getDialogue().getName() == null) sentence.getDialogue().setName(currentDialogueName);
            }
            //System.out.println(sentence.getDialogue());
        }

        //输出
        for (Sentence sentence : sentences){
            /*            System.out.println("-----------------------------------------------");
            System.out.println(sentence.getContent());
            Tree tree = sentence.getCoreMap().get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree.pennString());
            SemanticGraph graph = sentence.getCoreMap().get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(graph.toString(SemanticGraph.OutputFormat.LIST));*/
            //System.out.println(sentence.getContent());
            //System.out.println(sentence.getPronouns());
            //System.out.println(sentence.getDialogue());
        }
        //System.out.println(characters);

        StringBuffer outputXML = new StringBuffer();
        outputXML.append("<dialogues>\n\r");
        for (Sentence sentence : sentences)
            if (sentence.getDialogue().getContent() != null && sentence.getDialogue().getContent() != ""){
                outputXML.append("\t<dialogue>\n\r");
                outputXML.append("\t\t<name>"+sentence.getDialogue().getName()+"</name>\n\r");
                outputXML.append("\t\t<content>"+sentence.getDialogue().getContent()+"</content>\n\r");
                outputXML.append("\t</dialogue>\n\r");
            }
        outputXML.append("</dialogues>");
        FileUtil.outputXML(uploadpath,outputXML.toString());
    }
    public String findCharacter(String targetCharacter){
        for (String character : characters)
            if (character.indexOf(targetCharacter) >= 0) return character;
        return null;
    }

}
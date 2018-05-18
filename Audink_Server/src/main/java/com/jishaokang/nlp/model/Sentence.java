package com.jishaokang.nlp.model;

import edu.stanford.nlp.util.CoreMap;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NANA_Final on 2018/1/25.
 */
@Data
public class Sentence {

    private String content;
    private CoreMap coreMap;
    private Dialogue dialogue = null;
    private List<Pronoun> pronouns = null;

    public void addDialogue(){
        if (dialogue == null) dialogue = new Dialogue();
    }
    public void addPronouns(Pronoun pronoun){
        if (pronouns == null) pronouns = new ArrayList<Pronoun>();
        pronouns.add(pronoun);
    }

}
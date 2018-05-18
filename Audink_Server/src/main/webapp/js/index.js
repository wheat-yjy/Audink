//
var type = 1;
var audio,audioXS;
var abst,audioUrl,content;
function getTextView(){
    $("#online_type_text").addClass("online_type_active");
    $("#online_type_text_file").removeClass("online_type_active");
    $("#online_type_audio_file").removeClass("online_type_active");
    $("#online_text").removeClass("online_hidden");
    $("#online_text_file").addClass("online_hidden");
    $("#online_audio_file").addClass("online_hidden");
    type = 1;
    audio.src = "";
    audioXS.src = "";
    $("#summary_content").html("");
    $("#show_summary").show();
}
function getTextFileView(){
    $("#online_type_text").removeClass("online_type_active");
    $("#online_type_text_file").addClass("online_type_active");
    $("#online_type_audio_file").removeClass("online_type_active");
    $("#online_text").addClass("online_hidden");
    $("#online_text_file").removeClass("online_hidden");
    $("#online_audio_file").addClass("online_hidden");
    type = 2;
    audio.src = "";
    audioXS.src = "";
    $("#summary_content").html("");
    $("#show_summary").show();
}
function getAudioFileView(){
    $("#online_type_text").removeClass("online_type_active");
    $("#online_type_text_file").removeClass("online_type_active");
    $("#online_type_audio_file").addClass("online_type_active");
    $("#online_text").addClass("online_hidden");
    $("#online_text_file").addClass("online_hidden");
    $("#online_audio_file").removeClass("online_hidden");
    type = 3;
    audio.src = "";
    audioXS.src = "";
    $("#summary_content").html("");
    $("#show_summary").hide();
}
var foldTime1 = 0,foldTime2 = 0,foldTime3 = 0;
function fold1() {
    if (foldTime1 % 2 == 0){
        $('#plus1').show();
        $('#minus1').hide();
    }else{
        $('#plus1').hide();
        $('#minus1').show();
    }
    foldTime1++;
}
function fold2() {
    if (foldTime2 % 2 == 0){
        $('#plus2').show();
        $('#minus2').hide();
    }else{
        $('#plus2').hide();
        $('#minus2').show();
    }
    foldTime2++;
}
function fold3() {
    if (foldTime3 % 2 == 0){
        $('#plus3').show();
        $('#minus3').hide();
    }else{
        $('#plus3').hide();
        $('#minus3').show();
    }
    foldTime3++;
}
function dealText() {
    $.ajax({
        url: "api/user/dealText",
        type: 'POST',
        cache: false,
        data: new FormData($("#dealText")[0]),
        processData: false,
        contentType: false,
        beforeSend: function(){
            $('#dealing').html('正在合成');
        },
        success: function (result) {
            audio.src = "" + result.data.filename;
            audioXS.src = "" + result.data.filename;
            $("#summary_content").html(result.data.summary);
            $('#dealing').html('立即合成');
            audioUrl = result.data.filename;
            abst = result.data.summary;
            content = result.data.content;
        },
        error: function (err) {
            $('#dealing').html('立即合成');
        }
    });
}
function dealTextFile(){
    $.ajax({
        url: "api/user/dealTextFile",
        type: 'POST',
        cache: false,
        data: new FormData($("#dealTextFile")[0]),
        processData: false,
        contentType: false,
        beforeSend: function(){
            $('#dealing').html('正在合成');
        },
        success: function (result) {
            audio.src = "" + result.data.filename;
            audioXS.src = "" + result.data.filename;
            $("#summary_content").html(result.data.summary);
            $('#dealing').html('立即合成');
            audioUrl = result.data.filename;
            abst = result.data.summary;
            content = result.data.content;
        },
        error: function (err) {
            $('#dealing').html('立即合成');
        }
    });
}

function dealAudioFile(){
    $.ajax({
        url: "api/user/dealAudioFile",
        type: 'POST',
        cache: false,
        data: new FormData($("#dealAudioFile")[0]),
        processData: false,
        contentType: false,
        beforeSend: function(){
            $('#dealing').html('正在合成');
        },
        success: function (result) {
            audio.src = "" + result.data;
            audioXS.src = "" + result.data;
            $('#dealing').html('立即合成');
        },
        error: function (err) {
            $('#dealing').html('立即合成');
        }
    });
}
function deal() {
    if (type == 1) dealText();
    if (type == 2) dealTextFile();
    if (type == 3) dealAudioFile();
}
function init() {
    fold1();
    fold2();
    fold3();
    audio = $('#audio')[0];
    audioXS = $('#audioXS')[0];
    getUserName();
}
function getUserName() {
    $.post("api/user/getUserName",
        function(result){
            $("#username").html(result.data);
        });
}

function login() {
    var username = $("#loginName").val();
    var password = $("#loginPassword").val();
    $.post("api/all/login",{username:username,password:password},
        function(result){
            if (result.status ==200) alert("登录成功");
            else alert("用户名或密码错误")
        });
    getUserName();
}

function register() {
    var username = $("#registerName").val();
    var password = $("#registerPassword").val();
    $.post("api/all/register",{username:username,password:password},
        function(result){
            if (result.status ==200) alert("注册成功");
            else alert("用户名已存在");
        });
}

function deleteRecord(recordId) {
    $.post("api/user/deleteRecord",{recordId:recordId},
        function(result){
            getRecord();
        });
}
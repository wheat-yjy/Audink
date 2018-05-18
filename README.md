<br>Audink</br>
├── Android
<br>│   ├── androidTest</br>
│   │   └── java
<br>│   │       └── com</br>
│   │           └── kotlinproject
<br>│   │               └── wooooo</br>
│   │                   └── audink
<br>│   │                       ├── ExampleInstrumentedTest.kt</br>
│   │                       └── tool
<br>│   │                           └── PageTextCutterTest.kt</br>
│   ├── main
<br>│   │   ├── AndroidManifest.xml</br>
│   │   ├── java
<br>│   │   │   └── com</br>
│   │   │       └── kotlinproject
<br>│   │   │           └── wooooo</br>
│   │   │               └── audink
<br>│   │   │                   ├── activity</br>
│   │   │                   │   ├── BaseActivity.kt
<br>│   │   │                   │   ├── BookInfoActivity.kt</br>
│   │   │                   │   ├── ClassMoreBookActivity.kt
<br>│   │   │                   │   ├── LoginRegisterActivity.kt</br>
│   │   │                   │   ├── MainActivity.kt
<br>│   │   │                   │   ├── ReadActivity.kt</br>
│   │   │                   │   ├── SearchActivity.kt
<br>│   │   │                   │   └── WelcomeActivity.kt</br>
│   │   │                   ├── adapter
<br>│   │   │                   │   ├── ArticleListAdapter.kt</br>
│   │   │                   │   ├── BookRecommendGroupAdapter.kt
<br>│   │   │                   │   ├── PlayBarAdapter.kt</br>
│   │   │                   │   └── ShelfBookAdapter.kt
<br>│   │   │                   ├── database</br>
│   │   │                   │   └── AudinkDbHelper.kt
<br>│   │   │                   ├── fragment</br>
│   │   │                   │   ├── BookInfoArticleListFragment.kt
<br>│   │   │                   │   ├── BookInfoSummaryFragment.kt</br>
│   │   │                   │   ├── BookStoreFragment.kt
<br>│   │   │                   │   ├── LoginFragment.kt</br>
│   │   │                   │   ├── MyShelfFragment.kt
<br>│   │   │                   │   └── RegisterFragment.kt</br>
│   │   │                   ├── JsonInterface
<br>│   │   │                   ├── model</br>
│   │   │                   │   ├── ArticleInfo.kt
<br>│   │   │                   │   ├── BookInfo.kt</br>
│   │   │                   │   ├── BookStoreInfo.kt
<br>│   │   │                   │   ├── ClassifyBooksInfo.kt</br>
│   │   │                   │   ├── DataEmpty.kt
<br>│   │   │                   │   ├── JsonResponse.kt</br>
│   │   │                   │   ├── ResponseBooks.kt
<br>│   │   │                   │   └── UserId.kt</br>
│   │   │                   ├── MyTasks
<br>│   │   │                   ├── service</br>
│   │   │                   │   ├── BackgroundAudioService.kt
<br>│   │   │                   │   └── DownloadService.kt</br>
│   │   │                   ├── tool
<br>│   │   │                   │   ├── MusicPlayer.kt</br>
│   │   │                   │   ├── PageArticleCutter.kt
<br>│   │   │                   │   ├── PageFormater.kt</br>
│   │   │                   │   ├── PagePaintAdapter.kt
<br>│   │   │                   │   ├── PagePainter.kt</br>
│   │   │                   │   └── PageTextCutter.kt
<br>│   │   │                   ├── utils</br>
│   │   │                   │   ├── AppCache.kt
<br>│   │   │                   │   ├── DownloadUtil.kt</br>
│   │   │                   │   ├── FileUtil.kt
<br>│   │   │                   │   ├── SqlUtil.kt</br>
│   │   │                   │   └── UnitConversionUtil.kt
<br>│   │   │                   └── view</br>
│   │   │                       └── PageView.kt
<br>│   │   └── res</br>
│   │       ├── drawable
<br>│   │       │   ├── books.png</br>
│   │       │   ├── cover_book.jpg
<br>│   │       │   ├── ic_blank_document.xml</br>
│   │       │   ├── ic_book_black_24dp.xml
<br>│   │       │   ├── ic_chevron_right_black_24dp.xml</br>
│   │       │   ├── ic_download_interface_arrow_symbol.xml
<br>│   │       │   ├── ic_file_download_black_24dp.xml</br>
│   │       │   ├── ic_happy_man.xml
<br>│   │       │   ├── ic_info_outline_black_24dp.xml</br>
│   │       │   ├── ic_launcher_background.xml
<br>│   │       │   ├── ic_menu_black_24dp.xml</br>
│   │       │   ├── ic_more_vert_black_24dp.xml
<br>│   │       │   ├── ic_multimedia_menu.xml</br>
│   │       │   ├── ic_pause_button.xml
<br>│   │       │   ├── ic_pause_circle_outline_black_24dp.xml</br>
│   │       │   ├── ic_perm_identity_black_24dp.xml
<br>│   │       │   ├── ic_play_button__without_circle.xml</br>
│   │       │   ├── ic_play_button.xml
<br>│   │       │   ├── ic_play_circle_outline_black_24dp.xml</br>
│   │       │   ├── ic_priority_high_black_24dp.xml
<br>│   │       │   ├── ic_radio_black_24dp.xml</br>
│   │       │   ├── ic_science_book.xml
<br>│   │       │   ├── ic_search_black_24dp.xml</br>
│   │       │   ├── ic_search_white_24dp.xml
<br>│   │       │   ├── ic_settings_black_24dp.xml</br>
│   │       │   ├── ic_star_black_24dp.xml
<br>│   │       │   ├── ic_star_black.xml</br>
│   │       │   ├── ic_star_border_black_24dp.xml
<br>│   │       │   ├── ic_star_white.xml</br>
│   │       │   ├── ic_swap_vert_black_24dp.xml
<br>│   │       │   ├── ic_vpn_key_black_24dp.xml</br>
│   │       │   ├── logo_hd.png
<br>│   │       │   ├── logo_hd_sq_cir.png</br>
│   │       │   ├── logo_hd_sq.png
<br>│   │       │   ├── selector_more_gray_radius.xml</br>
│   │       │   ├── selector_player_button_background.xml
<br>│   │       │   └── selector_tab_bar_text_color.xml</br>
│   │       ├── drawable-v24
<br>│   │       │   └── ic_launcher_foreground.xml</br>
│   │       ├── layout
<br>│   │       │   ├── activity_book_info.xml</br>
│   │       │   ├── activity_classification_more_book.xml
<br>│   │       │   ├── activity_login_register.xml</br>
│   │       │   ├── activity_main.xml
<br>│   │       │   ├── activity_read.xml</br>
│   │       │   ├── activity_search.xml
<br>│   │       │   ├── activity_welcome.xml</br>
│   │       │   ├── fragment_book_info_article_list.xml
<br>│   │       │   ├── fragment_book_info_summary.xml</br>
│   │       │   ├── fragment_book_store.xml
<br>│   │       │   ├── fragment_login.xml</br>
│   │       │   ├── fragment_my_shelf.xml
<br>│   │       │   ├── fragment_register.xml</br>
│   │       │   ├── layout_book_article_item.xml
<br>│   │       │   ├── layout_book_store_recommend_group.xml</br>
│   │       │   ├── layout_book_store_recommend_item.xml
<br>│   │       │   ├── layout_navigation_header.xml</br>
│   │       │   ├── layout_player_bar.xml
<br>│   │       │   ├── layout_shelf_book_item.xml</br>
│   │       │   ├── layout_tab_bar.xml
<br>│   │       │   └── layout_toolbar.xml</br>
│   │       ├── menu
<br>│   │       │   ├── menu_book_list_cancel_prefer.xml</br>
│   │       │   ├── menu_book_list_prefer.xml
<br>│   │       │   ├── menu_navigation.xml</br>
│   │       │   ├── menu_read.xml
<br>│   │       │   └── menu_search.xml</br>
│   │       ├── mipmap-anydpi-v26
<br>│   │       │   ├── ic_launcher_round.xml</br>
│   │       │   └── ic_launcher.xml
<br>│   │       ├── mipmap-hdpi</br>
│   │       │   ├── ic_launcher.png
<br>│   │       │   └── ic_launcher_round.png</br>
│   │       ├── mipmap-mdpi
<br>│   │       │   ├── ic_launcher.png</br>
│   │       │   └── ic_launcher_round.png
<br>│   │       ├── mipmap-xhdpi</br>
│   │       │   ├── ic_launcher.png
<br>│   │       │   └── ic_launcher_round.png</br>
│   │       ├── mipmap-xxhdpi
<br>│   │       │   ├── ic_launcher.png</br>
│   │       │   └── ic_launcher_round.png
<br>│   │       ├── mipmap-xxxhdpi</br>
│   │       │   ├── ic_launcher.png
<br>│   │       │   └── ic_launcher_round.png</br>
│   │       ├── raw
<br>│   │       │   └── music_test.mp3</br>
│   │       └── values
<br>│   │           ├── attrs.xml</br>
│   │           ├── colors.xml
<br>│   │           ├── dimens.xml</br>
│   │           ├── strings.xml
<br>│   │           └── styles.xml</br>
│   └── test
<br>│       └── java</br>
│           └── com
<br>│               └── kotlinproject</br>
│                   └── wooooo
<br>│                       └── audink</br>
│                           └── ExampleUnitTest.kt
<br>├── Audink</br>
│   ├── bgm
<br>│   ├── data_load.py</br>
│   ├── data_process.py
<br>│   ├── effect.py</br>
│   ├── EmtAndBgm.py
<br>│   ├── __init__.py</br>
│   ├── ltp_data_v3.4.0
<br>│   ├── Models</br>
│   │   ├── audink_models
<br>│   │   │   ├── __init__.py</br>
│   │   │   ├── para
<br>│   │   │   │   ├── __init__.py</br>
│   │   │   │   ├── ParaClassifyModel_1.py
<br>│   │   │   │   ├── ParaClassify.py</br>
│   │   │   │   ├── __pycache__
<br>│   │   │   │   │   ├── __init__.cpython-36.pyc</br>
│   │   │   │   │   └── ParaClassify.cpython-36.pyc
<br>│   │   │   │   └── stop_word</br>
│   │   │   ├── __pycache__
<br>│   │   │   │   └── __init__.cpython-36.pyc</br>
│   │   │   ├── sentence
<br>│   │   │   │   ├── ClassifyModel4_3.py</br>
│   │   │   │   ├── __init__.py
<br>│   │   │   │   ├── __pycache__</br>
│   │   │   │   │   ├── __init__.cpython-36.pyc
<br>│   │   │   │   │   └── SentenceClassify.cpython-36.pyc</br>
│   │   │   │   ├── SentenceClassify.py
<br>│   │   │   │   └── stop_word</br>
│   │   │   └── subject
<br>│   │   │       ├── __init__.py</br>
│   │   │       ├── __pycache__
<br>│   │   │       │   ├── __init__.cpython-36.pyc</br>
│   │   │       │   └── SubjectClassify.cpython-36.pyc
<br>│   │   │       ├── stop_word</br>
│   │   │       ├── SubjectClassifyModel_1.py
<br>│   │   │       └── SubjectClassify.py</br>
│   │   ├── __init__.py
<br>│   │   └── __pycache__</br>
│   │       └── __init__.cpython-36.pyc
<br>│   ├── model_work.py</br>
│   ├── newneed
<br>│   │   ├── CART.ipynb</br>
│   │   ├── effect
<br>│   │   ├── need</br>
│   │   ├── no
<br>│   │   └── stop_word</br>
│   ├── renoise
<br>│   │   └── segan</br>
│   │       ├── bnorm.py
<br>│   │       ├── clean_wav.sh</br>
│   │       ├── data_loader.py
<br>│   │       ├── discriminator.py</br>
│   │       ├── generator.py
<br>│   │       ├── LICENCE</br>
│   │       ├── main.py
<br>│   │       ├── make_tfrecords.py</br>
│   │       ├── model.py
<br>│   │       └── ops.py</br>
│   ├── server.py
<br>│   ├── SQLWork.py</br>
│   ├── stop_word.txt
<br>│   └── voice_ocean</br>
│       ├── fileprocess.py
<br>│       ├── __init__.py</br>
│       ├── mainproc.py
<br>│       ├── pyvoice.py</br>
│       ├── Resample.py
<br>│       ├── sentence_define.py</br>
│       ├── test.py
<br>│       ├── threaduse.py</br>
│       ├── util
<br>│       │   ├── __init__.py</br>
│       │   ├── voice_emotion_analysis
<br>│       │   │   ├── OpenVokaturi-3-0-linux64.o</br>
│       │   │   ├── OpenVokaturi-3-0-linux64.so
<br>│       │   │   ├── OpenVokaturi-3-0-win64.dll</br>
│       │   │   ├── OpenVokaturi-3-0-win64.o
<br>│       │   │   ├── Vokaturi.h</br>
│       │   │   └── Vokaturi.py
<br>│       │   └── voice_emotion.py</br>
│       └── voice_recognition.py
<br>└── Audink_Server</br>
    ├── audink.sql
<br>    ├── pom.xml</br>
    └── src
<br>        └── main</br>
            ├── java
<br>            │   └── com</br>
            │       └── jishaokang
<br>            │           ├── base</br>
            │           │   ├── Result.java
<br>            │           │   ├── ResultStatusMessage.java</br>
            │           │   └── ResultStatusValues.java
<br>            │           ├── cache</br>
            │           │   └── ResultCache.java
<br>            │           ├── controller</br>
            │           │   ├── BookController.java
<br>            │           │   ├── CollectController.java</br>
            │           │   ├── CommonController.java
<br>            │           │   ├── DealController.java</br>
            │           │   ├── FileController.java
<br>            │           │   ├── RecordController.java</br>
            │           │   ├── UserController.java
<br>            │           │   └── UserWebController.java</br>
            │           ├── dao
<br>            │           │   ├── BookDAO.java</br>
            │           │   ├── ChapterDAO.java
<br>            │           │   ├── CollectDAO.java</br>
            │           │   ├── RecordDAO.java
<br>            │           │   └── UserDAO.java</br>
            │           ├── filter
<br>            │           │   └── AuthFilter.java</br>
            │           ├── model
<br>            │           │   ├── ClassifyBook.java</br>
            │           │   ├── dto
<br>            │           │   │   ├── Book.java</br>
            │           │   │   ├── Chapter.java
<br>            │           │   │   ├── Collect.java</br>
            │           │   │   ├── Record.java
<br>            │           │   │   └── User.java</br>
            │           │   └── vo
<br>            │           │       ├── BookVO.java</br>
            │           │       ├── ClassifyVO.java
<br>            │           │       ├── CollectVO.java</br>
            │           │       └── HomepageVO.java
<br>            │           ├── nlp</br>
            │           │   ├── model
<br>            │           │   │   ├── Dialogue.java</br>
            │           │   │   ├── Pronoun.java
<br>            │           │   │   └── Sentence.java</br>
            │           │   └── util
<br>            │           │       ├── ExpressVerbUtil.java</br>
            │           │       ├── QuoteUtil.java
<br>            │           │       └── ReplacePronouns.java</br>
            │           ├── service
<br>            │           │   ├── BookService.java</br>
            │           │   ├── CollectService.java
<br>            │           │   ├── CommonService.java</br>
            │           │   ├── DealService.java
<br>            │           │   ├── impl</br>
            │           │   │   ├── BookServiceImpl.java
<br>            │           │   │   ├── CollectServiceImpl.java</br>
            │           │   │   ├── CommonServiceImpl.java
<br>            │           │   │   ├── DealServiceImpl.java</br>
            │           │   │   ├── RecordServiceImpl.java
<br>            │           │   │   ├── UserServiceImpl.java</br>
            │           │   │   └── UserWebServiceImpl.java
<br>            │           │   ├── RecordService.java</br>
            │           │   ├── UserService.java
<br>            │           │   └── UserWebService.java</br>
            │           ├── test
<br>            │           │   ├── test2.java</br>
            │           │   └── test.java
<br>            │           └── util</br>
            │               ├── Cryptographic.java
<br>            │               ├── FileUtil.java</br>
            │               ├── IPUtil.java
<br>            │               ├── main.java</br>
            │               └── PythonUtil.java
<br>            ├── resources</br>
            │   ├── jdbc.properties
<br>            │   ├── log4j.properties</br>
            │   ├── mapper
<br>            │   │   ├── Book-mapper.xml</br>
            │   │   ├── Chapter-mapper.xml
<br>            │   │   ├── Collect-mapper.xml</br>
            │   │   ├── Record-mapper.xml
<br>            │   │   └── User-mapper.xml</br>
            │   ├── mybatisConfig.xml
<br>            │   ├── spring-jdbc.xml</br>
            │   ├── spring-mvc.xml
<br>            │   └── spring.xml</br>
            └── webapp
<br>                ├── book.html</br>
                ├── css
<br>                │   ├── ace-ie.min.css</br>
                │   ├── ace.min.css
<br>                │   ├── ace-rtl.min.css</br>
                │   ├── ace-skins.min.css
<br>                │   ├── bootstrap.css</br>
                │   ├── bootstrap-editable.css
<br>                │   ├── bootstrap.min.css</br>
                │   ├── bootstrap-timepicker.css
<br>                │   ├── chosen.css</br>
                │   ├── colorbox.css
<br>                │   ├── colorpicker.css</br>
                │   ├── datepicker.css
<br>                │   ├── daterangepicker.css</br>
                │   ├── dropzone.css
<br>                │   ├── font-awesome-ie7.min.css</br>
                │   ├── font-awesome.min.css
<br>                │   ├── fullcalendar.css</br>
                │   ├── images
<br>                │   │   └── loading.gif</br>
                │   ├── index.css
<br>                │   ├── jquery.gritter.css</br>
                │   ├── jquery-ui-1.10.3.custom.min.css
<br>                │   ├── jquery-ui-1.10.3.full.min.css</br>
                │   ├── manage.css
<br>                │   ├── select2.css</br>
                │   └── ui.jqgrid.css
<br>                ├── fonts</br>
                │   ├── glyphicons-halflings-regular.eot
<br>                │   ├── glyphicons-halflings-regular.svg</br>
                │   ├── glyphicons-halflings-regular.ttf
<br>                │   ├── glyphicons-halflings-regular.woff</br>
                │   └── glyphicons-halflings-regular.woff2
<br>                ├── img</br>
                │   ├── 5-121204193R5-50.gif
<br>                │   ├── add.png</br>
                │   ├── analyze.png
<br>                │   ├── audioFile.png</br>
                │   ├── background.jpg
<br>                │   ├── collect.png</br>
                │   ├── cost.png
<br>                │   ├── deal.png</br>
                │   ├── default_headimg.png
<br>                │   ├── delete.png</br>
                │   ├── edit.png
<br>                │   ├── footer.jpg</br>
                │   ├── inputText.png
<br>                │   ├── like.png</br>
                │   ├── metion.png
<br>                │   ├── people.png</br>
                │   ├── sound.png
<br>                │   ├── textFile.png</br>
                │   ├── text.png
<br>                │   ├── uncollect.png</br>
                │   └── upload.png
<br>                ├── index.html</br>
                ├── js
<br>                │   ├── ace-elements.min.js</br>
                │   ├── ace-extra.min.js
<br>                │   ├── ace.min.js</br>
                │   ├── additional-methods.min.js
<br>                │   ├── bootbox.min.js</br>
                │   ├── bootstrap-colorpicker.min.js
<br>                │   ├── bootstrap.js</br>
                │   ├── bootstrap.min.js
<br>                │   ├── bootstrap-tag.min.js</br>
                │   ├── bootstrap-wysiwyg.min.js
<br>                │   ├── chosen.jquery.min.js</br>
                │   ├── date-time
<br>                │   │   ├── bootstrap-datepicker.min.js</br>
                │   │   ├── bootstrap-timepicker.min.js
<br>                │   │   ├── daterangepicker.min.js</br>
                │   │   └── moment.min.js
<br>                │   ├── dropzone.min.js</br>
                │   ├── excanvas.min.js
<br>                │   ├── flot</br>
                │   │   ├── jquery.flot.min.js
<br>                │   │   ├── jquery.flot.pie.min.js</br>
                │   │   └── jquery.flot.resize.min.js
<br>                │   ├── fuelux</br>
                │   │   ├── data
<br>                │   │   │   └── fuelux.tree-sampledata.js</br>
                │   │   ├── fuelux.spinner.min.js
<br>                │   │   ├── fuelux.tree.min.js</br>
                │   │   └── fuelux.wizard.min.js
<br>                │   ├── fullcalendar.min.js</br>
                │   ├── html5shiv.js
<br>                │   ├── index.js</br>
                │   ├── jqGrid
<br>                │   │   ├── i18n</br>
                │   │   │   └── grid.locale-en.js
<br>                │   │   └── jquery.jqGrid.min.js</br>
                │   ├── jquery-1.10.2.min.js
<br>                │   ├── jquery-2.0.3.min.js</br>
                │   ├── jquery.autosize.min.js
<br>                │   ├── jquery.colorbox-min.js</br>
                │   ├── jquery.dataTables.bootstrap.js
<br>                │   ├── jquery.dataTables.min.js</br>
                │   ├── jquery.easy-pie-chart.min.js
<br>                │   ├── jquery.gritter.min.js</br>
                │   ├── jquery.hotkeys.min.js
<br>                │   ├── jquery.inputlimiter.1.3.1.min.js</br>
                │   ├── jquery.knob.min.js
<br>                │   ├── jquery.maskedinput.min.js</br>
                │   ├── jquery.min.js
<br>                │   ├── jquery.mobile.custom.min.js</br>
                │   ├── jquery.nestable.min.js
<br>                │   ├── jquery.slimscroll.min.js</br>
                │   ├── jquery.sparkline.min.js
<br>                │   ├── jquery-ui-1.10.3.custom.min.js</br>
                │   ├── jquery-ui-1.10.3.full.min.js
<br>                │   ├── jquery.ui.touch-punch.min.js</br>
                │   ├── jquery.validate.min.js
<br>                │   ├── markdown</br>
                │   │   ├── bootstrap-markdown.min.js
<br>                │   │   └── markdown.min.js</br>
                │   ├── respond.min.js
<br>                │   ├── select2.min.js</br>
                │   ├── typeahead-bs2.min.js
<br>                │   └── x-editable</br>
                │       ├── ace-editable.min.js
<br>                │       └── bootstrap-editable.min.js</br>
                ├── upload
<br>                │   ├── ExpressVerbs.txt</br>
                │   ├── img
<br>                │   ├── recommend</br>
                │   └── StanfordCoreNLP-chinese.properties
<br>                ├── uploadBook.html</br>
                ├── uploadChapter.html
<br>                └── WEB-INF</br>
                    └── web.xml

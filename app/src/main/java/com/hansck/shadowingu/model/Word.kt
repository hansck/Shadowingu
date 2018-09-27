package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Word(
        @PrimaryKey()
        var idWord: Int,

        @ColumnInfo(name = "kanji")
        var kanji: String,

        @ColumnInfo(name = "furigana")
        var furigana: String,

        @ColumnInfo(name = "romaji")
        var romaji: String,

        @ColumnInfo(name = "meaning")
        var meaning: String,

        @ColumnInfo(name = "audio")
        var audio: String,

        @ColumnInfo(name = "template")
        var template: String,

        @ColumnInfo(name = "topic")
        var stage: Int) {

    companion object {
        fun populateData(): Array<Word> {
            return arrayOf(
                    Word(0, "私", "わたし", "Watashi", "I", "watashi", "r_watashi", 0),
                    Word(1, "貴方", "あなた ", "Anata", "You", "anata", "r_anata", 0),
                    Word(2, "彼 ", "かれ", "Kare", "He", "kare", "r_kare", 0),
                    Word(3, "彼女 ", "かのじょ", "Kanojo", "She", "kanojo", "r_kanojo", 0),
                    Word(4, "女性 ", "じょせい", "Josei", "Man", "josei", "r_josei", 0),
                    Word(5, "男性", "だんせい", "Dansei", "Woman", "dansei", "r_dansei", 0),
                    Word(6, "お父さん", "お父さん", "Otousan", "Father", "otousan", "r_otousan", 0),
                    Word(7, "お母さん", "おかあさん", "Okaasan", "Mother", "okaasan", "r_okaasan", 0),
                    Word(8, "子供", "こども", "Kodomo", "Child", "kodomo", "r_kodomo", 0),
                    Word(9, "家族", "かぞく", "Kazoku", "Family", "kazoku", "r_kazoku", 0),

                    Word(10, "こんにちは", "こんにちは", "Konnichiwa", "Hello", "konnichiwa", "r_konnichiwa", 1),
                    Word(11, "さようなら", "さようなら", "Sayounara", "Good Bye", "sayounara", "r_sayounara", 1),
                    Word(12, "元気です", "げんきです", "Genki desu", "Good Health", "genki", "r_genki", 1),
                    Word(13, "学校です", "がっこうです", "Gakkou desu", "School", "gakkou", "r_gakkou", 1),
                    Word(14, "先生です", "せんせいです", "Sensei desu", "Teacher", "sensei", "r_sensei", 1),
                    Word(15, "生徒です", "せいとです", "Seito desu", "Student", "seito", "r_seito", 1),
                    Word(16, "クラスです", "クラスです", "Kurasu desu", "Class", "kurasu", "r_kurasu", 1),
                    Word(17, "友達です", "ともだちです", "Tomodachi desu", "Friend", "tomodachi", "r_tomodachi", 1),
                    Word(18, "理解", "りかい", "Rikai", "Understand", "rikai", "r_rikai", 1),
                    Word(19, "質問", "しつもん", "Shitsumon", "Question", "shitsumon", "r_shitsumon", 1),

                    Word(20, "勉強をします", "べんきょうをします", "Benkyou o shimasu", "Study", "benkyouoshimasu", "r_benkyouoshimasu", 2),
                    Word(21, "習います", "ならいます", "Naraimasu", "Learn", "naraimasu", "r_naraimasu", 2),
                    Word(22, "読みます", "よみます", "Yomimasu", "Read", "yomimasu", "r_yomimasu", 2),
                    Word(23, "書きます", "かきます", "Kakimasu", "Write", "kakimasu", "r_kakimasu", 2),
                    Word(24, "話します", "はなします", "Hanashimasu", "Speak", "hanashimasu", "r_hanashimasu", 2),
                    Word(25, "答えます", "こたえます", "Kotaemasu", "Answer", "kotaemasu", "r_kotaemasu", 2),
                    Word(26, "行きます", "いきます", "Ikimasu", "Go", "ikimasu", "r_ikimasu", 2),
                    Word(27, "来る", "くる", "Kuru", "Come", "kuru", "r_kuru", 2),
                    Word(28, "働く", "はたらく", "Hataraku", "Work", "hataraku", "r_hataraku", 2),
                    Word(29, "好きです", "すきです", "Suki desu", "Like", "suki", "r_suki", 2),

                    Word(30, "珈琲", "コーヒー", "Koohii", "Coffee", "koohii", "r_koohii", 3),
                    Word(31, "ミネラルウォーター", "ミネラルウォーター", "Mineraruwota", "Mineral Water", "mineraruwota", "r_mineraruwota", 3),
                    Word(32, "砂糖", "さとう", "Satou", "Sugar", "satou", "r_satou", 3),
                    Word(33, "パーティー", "パーティー", "Pati", "Party", "pati", "r_pati", 3),
                    Word(34, "ワイン", "ワイン", "Wain", "Wine", "wain", "r_wain", 3),
                    Word(35, "ミルク", "ミルク", "Miruku", "Milk", "miruku", "r_miruku", 3),
                    Word(36, "トースト", "トースト", "Tosuto", "Toast", "tosuto", "r_tosuto", 3),
                    Word(37, "パン", "パン", "Pan", "Bread", "pan", "r_pan", 3),
                    Word(38, "スーパーマーケット", "スーパーマーケット", "Supamaketto", "Supermarket", "supamaketto", "r_supamaketto", 3),
                    Word(39, "テーブル", "テーブル", "Teburu", "Table", "teburu", "r_teburu", 3),

                    Word(40, "食べます", "たべます", "Tabemasu", "Eat", "tabemasu", "r_tabemasu", 4),
                    Word(41, "飲みます", "のみます", "Nomimasu", "Drink", "nomimasu", "r_nomimasu", 4),
                    Word(42, "下さい", "ください", "Kudasai", "Please", "kudasai", "r_kudasai", 4),
                    Word(43, "お勧め", "おすすめ", "Osusume", "Recommendation", "osusume", "r_osusume", 4),
                    Word(44, "お願いします", "おねがいします", "Onegaishimasu", "Please, Thank You", "onegaishimasu", "r_onegaishimasu", 4),
                    Word(45, "野菜", "やさい", "Yasai", "Vegetable", "yasai", "r_yasai", 4),
                    Word(46, "牛肉", "ぎゅにく", "Gyuniku", "Beef", "gyuniku", "r_gyuniku", 4),
                    Word(47, "豚肉", "ぶたにく", "Butaniku", "Pork", "butaniku", "r_butaniku", 4),
                    Word(48, "魚", "さかな", "Sakana", "Fish", "sakana", "r_sakana", 4),
                    Word(49, "昼ごはん", "ひるごはん", "Hirugohan", "Lunch", "hirugohan", "r_hirugohan", 4),

                    Word(50, "動物園", "どうぶつえん", "Doubutsuen", "Zoo", "doubutsuen", "r_doubutsuen", 5),
                    Word(51, "美術館", "びじゅつかん", "Bijutsukan", "Museum", "bijutsukan", "r_bijutsukan", 5),
                    Word(52, "都市", "とし", "Toshi", "City", "toshi", "r_toshi", 5),
                    Word(53, "映画館", "えいがかん", "Eigakan", "Cinema", "eigakan", "r_eigakan", 5),
                    Word(54, "建物", "たてもの", "Tatemono", "Building", "tatemono", "r_tatemono", 5),
                    Word(55, "市場", "いちば", "Ichiba", "Market", "ichiba", "r_ichiba", 5),
                    Word(56, "展覧会", "てんらんかい", "Tenrankai", "Exhibition", "tenrankai", "r_tenrankai", 5),
                    Word(57, "デパット", "デパット", "Depatto", "Department Store", "depatto", "r_depatto", 5),
                    Word(58, "ギャラリー", "ギャラリー", "Gyarari", "Gallery", "gyarari", "r_gyarari", 5),
                    Word(59, "駐車場", "ちゅうしゃじょう", "Chuushajou", "Parking Lot", "chuushajou", "r_chuushajou", 5),

                    Word(60, "地下鉄", "ちかてつ", "Chikatetsu", "Train", "chikatetsu", "r_chikatetsu", 6),
                    Word(61, "乗り換え", "のりかえ", "Norikae", "Transfer", "norikae", "r_norikae", 6),
                    Word(62, "罰金", "ばっきん", "Bakkin", "Fine", "bakkin", "r_bakkin", 6),
                    Word(63, "荷物", "にもつ", "Nimotsu", "Luggage", "nimotsu", "r_nimotsu", 6),
                    Word(64, "忘れ物", "わすれもの", "Wasuremono", "Lost Item", "wasuremono", "r_wasuremono", 6),
                    Word(65, "買い物", "かいもの", "Kaimono", "Shopping", "kaimono", "r_kaimono", 6),
                    Word(66, "暖房", "だんぼう", "Danbou", "Heating", "danbou", "r_danbou", 6),
                    Word(67, "到着", "とうちゃく", "Touchaku", "Arrival", "touchaku", "r_touchaku", 6),
                    Word(68, "写真", "しゃしん", "Shashin", "Photograph", "shashin", "r_shashin", 6),
                    Word(69, "運転します", "うんてんします", "Untenshimasu", "Driving", "untenshimasu", "r_untenshimasu", 6))
        }
    }
}
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

        @ColumnInfo(name = "level")
        var stage: Int) {

    companion object {
        fun populateData(): Array<Word> {
            return arrayOf(
                    Word(0, "私", "わたし", "Watashi", "I", "watashi", 0),
                    Word(1, "貴方", "あなた ", "Anata", "You", "anata", 0),
                    Word(2, "彼 ", "かれ", "Kare", "He", "kare", 0),
                    Word(3, "彼女 ", "かのじょ", "Kanojo", "She", "kanojo", 0),
                    Word(4, "女性 ", "じょせい", "Josei", "Man", "josei", 0),
                    Word(5, "男性", "だんせい", "Dansei", "Woman", "dansei", 0),
                    Word(6, "お父さん", "お父さん", "Otousan", "Father", "otousan", 0),
                    Word(7, "お母さん", "おかあさん", "Okaasan", "Mother", "okaasan", 0),
                    Word(8, "子供", "こども", "Kodomo", "Child", "kodomo", 0),
                    Word(9, "家族", "かぞく", "Kazoku", "Family", "kazoku", 0),

                    Word(10, "こんにちは", "こんにちは", "Konnichiwa", "Hello", "konnichiwa", 1),
                    Word(11, "さようなら", "さようなら", "Sayounara", "Good Bye", "sayounara", 1),
                    Word(12, "元気です", "げんきです", "Genki desu", "Good Health", "genki", 1),
                    Word(13, "学校です", "がっこうです", "Gakkou desu", "School", "gakkou", 1),
                    Word(14, "先生です", "せんせいです", "Sensei desu", "Teacher", "sensei", 1),
                    Word(15, "生徒です", "せいとです", "Seito desu", "Student", "seito", 1),
                    Word(16, "クラスです", "クラスです", "Kurasu desu", "Class", "kurasu", 1),
                    Word(17, "友達です", "ともだちです", "Tomodachi desu", "Friend", "tomodachi", 1),
                    Word(18, "理解", "りかい", "Rikai", "Understand", "rikai", 1),
                    Word(19, "質問", "しつもん", "Shitsumon", "Question", "shitsumon", 1),

                    Word(20, "勉強をします", "べんきょうをします", "Benkyou o shimasu", "Study", "benkyouoshimasu", 2),
                    Word(21, "習います", "ならいます", "Naraimasu", "Learn", "naraimasu", 2),
                    Word(22, "読みます", "よみます", "Yomimasu", "Read", "yomimasu", 2),
                    Word(23, "書きます", "かきます", "Kakimasu", "Write", "kakimasu", 2),
                    Word(24, "話します", "はなします", "Hanashimasu", "Speak", "hanashimasu", 2),
                    Word(25, "答えます", "こたえます", "Kotaemasu", "Answer", "kotaemasu", 2),
                    Word(26, "行きます", "いきます", "Ikimasu", "Go", "ikimasu", 2),
                    Word(27, "来る", "くる", "Kuru", "Come", "kuru", 2),
                    Word(28, "働く", "はたらく", "Hataraku", "Work", "hataraku", 2),
                    Word(29, "好きです", "すきです", "Suki desu", "Like", "suki", 2),

                    Word(30, "珈琲", "コーヒー", "Koohii", "Coffee", "koohii", 3),
                    Word(31, "ミネラルウォーター", "ミネラルウォーター", "Mineraruwota", "Mineral Water", "mineraruwota", 3),
                    Word(32, "砂糖", "さとう", "Satou", "Sugar", "satou", 3),
                    Word(33, "パーティー", "パーティー", "Pati", "Party", "pati", 3),
                    Word(34, "ワイン", "ワイン", "Wain", "Wine", "wain", 3),
                    Word(35, "ミルク", "ミルク", "Miruku", "Milk", "miruku", 3),
                    Word(36, "トースト", "トースト", "Tosuto", "Toast", "tosuto", 3),
                    Word(37, "パン", "パン", "Pan", "Bread", "pan", 3),
                    Word(38, "スーパーマーケット", "スーパーマーケット", "Supamaketto", "Supermarket", "supamaketto", 3),
                    Word(39, "テーブル", "テーブル", "Teburu", "Table", "teburu", 3),

                    Word(40, "食べます", "たべます", "Tabemasu", "Eat", "tabemasu", 4),
                    Word(41, "飲みます", "のみます", "Nomimasu", "Drink", "nomimasu", 4),
                    Word(42, "下さい", "ください", "Kudasai", "Please", "kudasai", 4),
                    Word(43, "お勧め", "おすすめ", "Osusume", "Recommendation", "osusume", 4),
                    Word(44, "お願いします", "おねがいします", "Onegaishimasu", "Please, Thank You", "onegaishimasu", 4),
                    Word(45, "野菜", "やさい", "Yasai", "Vegetable", "yasai", 4),
                    Word(46, "牛肉", "ぎゅにく", "Gyuniku", "Beef", "gyuniku", 4),
                    Word(47, "豚肉", "ぶたにく", "Butaniku", "Pork", "butaniku", 4),
                    Word(48, "魚", "さかな", "Sakana", "Fish", "sakana", 4),
                    Word(49, "昼ごはん", "ひるごはん", "Hirugohan", "Lunch", "hirugohan", 4))
        }
    }
}
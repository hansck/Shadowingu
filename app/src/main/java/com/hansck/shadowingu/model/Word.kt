package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Word(
        @PrimaryKey(autoGenerate = true)
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
                    Word(1, "私", "わたし", "Watashi", "I", "watashi", 1),
                    Word(2, "貴方", "あなた ", "Anata", "You", "anata", 1),
                    Word(3, "彼 ", "かれ", "Kare", "He", "kare", 1),
                    Word(4, "彼女 ", "かのじょ", "Kanojo", "She", "kanojo", 1),
                    Word(5, "女性 ", "じょせい", "Josei", "Man", "josei", 1),
                    Word(6, "男性", "だんせい", "Dansei", "Woman", "dansei", 1),
                    Word(7, "お父さん", "お父さん", "Otousan", "Father", "otousan", 1),
                    Word(8, "お母さん", "おかあさん", "Okaasan", "Mother", "okaasan", 1),
                    Word(9, "家", "いえ", "Ie", "House", "ie", 1),
                    Word(10, "家族", "かぞく", "Kazoku", "Family", "kazoku", 1),

                    Word(11, "こんにちは", "こんにちは", "Konnichiwa", "Hello", "konnichiwa", 2),
                    Word(12, "さようなら", "さようなら", "Sayounara", "Good Bye", "sayounara", 2),
                    Word(13, "元気", "げんき", "Genki", "Good Health", "genki", 2),
                    Word(14, "学校", "がっこう", "Gakkou", "School", "gakkou", 2),
                    Word(15, "先生", "せんせい", "Sensei", "Teacher", "sensei", 2),
                    Word(16, "生徒", "せいと", "Seito", "Student", "seito", 2),
                    Word(17, "クラス", "クラス", "Kurasu", "Class", "kurasu", 2),
                    Word(18, "授業", "じゅぎょう", "Jugyou", "Lesson", "jugyou", 2),
                    Word(19, "友達", "ともだち", "Tomodachi", "Friend", "tomodachi", 2),
                    Word(20, "本", "ほん", "Hon", "Book", "hon", 2),

                    Word(21, "勉強おします", "べんきょうおします", "Benkyou o shimasu", "Study", "benkyouoshimasu", 3),
                    Word(22, "習います", "ならいます", "Naraimasu", "Learn", "naraimasu", 3),
                    Word(23, "読みます", "よみます", "Yomimasu", "Read", "yomimasu", 3),
                    Word(24, "書きます", "かきます", "Kakimasu", "Write", "kakimasu", 3),
                    Word(25, "話します", "はなします", "Hanashimasu", "Speak", "hanashimasu", 3),
                    Word(26, "有ります", "あります", "Arimasu", "Exist (Not-Living)", "arimasu", 3),
                    Word(27, "働きます", "はたらきます", "Hatarakimasu", "Work", "hatarakimasu", 3),
                    Word(28, "います", "います", "Imasu", "Exist (Living)", "imasu", 3),
                    Word(29, "します", "します", "Shimasu", "Do", "shimasu", 3),
                    Word(30, "好き", "すき", "Suki", "Like", "suki", 3),

                    Word(31, "珈琲", "コーヒー", "Koohii", "Coffee", "koohii", 4),
                    Word(32, "ミネラルウォーター", "ミネラルウォーター", "Mineraruwota", "Mineral Water", "mineraruwota", 4),
                    Word(33, "砂糖", "さとう", "Satou", "Sugar", "satou", 4),
                    Word(34, "パーティー", "パーティー", "Pati", "Party", "pati", 4),
                    Word(35, "ワイン", "ワイン", "Wain", "Wine", "wain", 4),
                    Word(36, "ミルク", "ミルク", "Miruku", "Milk", "miruku", 4),
                    Word(37, "トースト", "トースト", "Tosuto", "Toast", "tosuto", 4),
                    Word(38, "パン", "パン", "Pan", "Bread", "pan", 4),
                    Word(39, "スーパーマーケット", "スーパーマーケット", "Supamaketto", "Supermarket", "supamaketto", 4),
                    Word(40, "テーブル", "テーブル", "Teburu", "Table", "teburu", 4),

                    Word(41, "食べます", "たべます", "Tabemasu", "Eat", "tabemasu", 5),
                    Word(42, "飲みます", "のみます", "Nomimasu", "Drink", "nomimasu", 5),
                    Word(43, "下さい", "ください", "Kudasai", "Please", "kudasai", 5),
                    Word(44, "お勧め", "おすすめ", "Osusume", "Recommendation", "osusume", 5),
                    Word(45, "お願いします", "おねがいします", "Onegaishimasu", "Please, Thank You", "onegaishimasu", 5),
                    Word(46, "野菜", "やさい", "Yasai", "Vegetable", "yasai", 5),
                    Word(47, "牛肉", "ぎゅにく", "Gyuniku", "Beef", "gyuniku", 5),
                    Word(48, "豚肉", "ぶたにく", "Butaniku", "Pork", "butaniku", 5),
                    Word(49, "魚", "さかな", "Sakana", "Fish", "sakana", 5),
                    Word(50, "昼ごはん", "ひるごはん", "Hirugohan", "Lunch", "hirugohan", 5))
        }
    }
}
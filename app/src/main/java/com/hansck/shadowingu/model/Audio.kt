package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity(foreignKeys = arrayOf(ForeignKey(entity = Stage::class,
        parentColumns = arrayOf("idStage"), childColumns = arrayOf("stage"))))
data class Audio(
        @PrimaryKey(autoGenerate = true)
        var idAudio: Long,

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

    fun populateData(): Array<Audio> {
        return arrayOf(
                Audio(1, "私", "わたし", "Watashi", "I", "watashi", 1),
                Audio(2, "貴方", "あなた ", "Anata", "You", "anata", 1),
                Audio(3, "彼 ", "かれ", "Kare", "He", "kare", 1),
                Audio(4, "彼女 ", "かのじょ", "Kanojo", "She", "kanojo", 1),
                Audio(5, "女性 ", "じょせい", "Josei", "Man", "josei", 1),
                Audio(6, "男性", "だんせい", "Dansei", "Woman", "dansei", 1),
                Audio(7, "お父さん", "お父さん", "Otousan", "Father", "otousan", 1),
                Audio(8, "お母さん", "おかあさん", "Okaasan", "Mother", "okaasan", 1),
                Audio(9, "家", "いえ", "Ie", "House", "ie", 1),
                Audio(10, "家族", "かぞく", "Kazoku", "Family", "kazoku", 1),

                Audio(11, "こんにちは", "こんにちは", "Konnichiwa", "Hello", "konnichiwa", 2),
                Audio(12, "さようなら", "さようなら", "Sayounara", "Good Bye", "sayounara", 2),
                Audio(13, "元気", "げんき", "Genki", "Good Health", "genki", 2),
                Audio(14, "学校", "がっこう", "Gakkou", "School", "gakkou", 2),
                Audio(15, "先生", "せんせい", "Sensei", "Teacher", "sensei", 2),
                Audio(16, "生徒", "せいと", "Seito", "Student", "saito", 2),
                Audio(17, "クラス", "クラス", "Kurasu", "Class", "kurasu", 2),
                Audio(18, "授業", "じゅぎょう", "Jugyou", "Lesson", "jugyou", 2),
                Audio(19, "友達", "ともだち", "Tomodachi", "Friend", "tomodachi", 2),

                Audio(20, "本", "ほん", "Hon", "Book", "hon", 2),
                Audio(21, "勉強おします", "べんきょうおします", "Benkyou o shimasu", "Study", "benkyouoshimasu", 3),
                Audio(22, "習います", "ならいます", "Naraimasu", "Learn", "naraimasu", 3),
                Audio(23, "読みます", "よみます", "Yomimasu", "Read", "yomimasu", 3),
                Audio(24, "書きます", "かきます", "Kakimasu", "Write", "kakimasu", 3),
                Audio(25, "話します", "はなします", "Hanashimasu", "Speak", "hanashimasu", 3),
                Audio(26, "有ります", "あります", "Arimasu", "Exist (Not-Living)", "arimasu", 3),
                Audio(27, "働きます", "はたらきます", "Hatarakimasu", "Work", "hatarakimasu", 3),
                Audio(28, "います", "います", "Imasu", "Exist (Living)", "imasu", 3),
                Audio(29, "します", "します", "Shimasu", "Do", "shimasu", 3),
                Audio(30, "好き", "すき", "Suki", "Like", "suki", 3),

                Audio(31, "珈琲", "コーヒー", "Koohii", "Coffee", "kohi", 4),
                Audio(32, "ミネラルウォーター", "ミネラルウォーター", "Mineraruwota", "Mineral Water", "mineraruwota", 4),
                Audio(33, "砂糖", "さとう", "Satou", "Sugar", "satou", 4),
                Audio(34, "パーティー", "パーティー", "Pati", "Party", "pati", 4),
                Audio(35, "ワイン", "ワイン", "Wain", "Wine", "wain", 4),
                Audio(36, "ミルク", "ミルク", "Miruku", "Milk", "miruku", 4),
                Audio(37, "トースト", "トースト", "Tosuto", "Toast", "tosuto", 4),
                Audio(38, "パン", "パン", "Pan", "Bread", "pan", 4),
                Audio(39, "スーパーマーケット", "スーパーマーケット", "Supamaketto", "Supermarket", "supamaketto", 4),
                Audio(40, "テーブル", "テーブル", "Teburu", "Table", "teburu", 4),

                Audio(41, "食べます", "たべます", "Tabemasu", "Eat", "tabemasu", 5),
                Audio(42, "飲みます", "のみます", "Nomimasu", "Drink", "nomimasu", 5),
                Audio(43, "下さい", "ください", "Kudasai", "Please", "kudasai", 5),
                Audio(44, "お勧め", "おすすめ", "Osusume", "Recommendation", "osusume", 5),
                Audio(45, "お願いします", "おねがいします", "Onegaishimasu", "Please, Thank You", "onegaishimasu", 5),
                Audio(46, "野菜", "やさい", "Yasai", "Vegetable", "yasai", 5),
                Audio(47, "牛肉", "ぎゅにく", "Gyuniku", "Beef", "gyuniku", 5),
                Audio(48, "豚肉", "ぶたにく", "Butaniku", "Pork", "butaniku", 5),
                Audio(49, "魚", "さかな", "Sakana", "Fish", "sakana", 5),
                Audio(50, "昼ごはん", "ひるごはん", "Hirugohan", "Lunch", "hirugohan", 5))
    }
}
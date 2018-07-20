package aleksandrkim.sampleapplication.db.models

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 8:59 PM for SampleApplication
 */
data class Article (var source: Source,
                    var author: String?,
                    val title: String,
                    val description: String,
                    val url: String,
                    val ultToImage: String,
                    val publishedAt: String) {

    val authorName: String = source.name

}
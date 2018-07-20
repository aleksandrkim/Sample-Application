package aleksandrkim.sampleapplication.db.models

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 8:43 PM for SampleApplication
 */

data class TopHeadline(var status: String = "",
                       var totalResults: Long = -1,
                       var articles: List<Article>) {

}
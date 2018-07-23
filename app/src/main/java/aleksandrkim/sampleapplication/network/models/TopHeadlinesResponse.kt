package aleksandrkim.sampleapplication.network.models

import aleksandrkim.sampleapplication.db.entities.Article

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 8:43 PM for SampleApplication
 */

data class TopHeadlinesResponse(override var status: String = "",
                                override var code: String? = "",
                                override var message: String? = "",
                                var totalResults: Long? = -1,
                                var articles: List<Article>?): NewsApiResponse

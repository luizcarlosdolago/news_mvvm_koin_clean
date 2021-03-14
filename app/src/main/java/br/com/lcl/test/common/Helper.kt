package br.com.lcl.test.common

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.SpannedString

class Helper {
    companion object {
        fun formatDateTimeWithAuthor(date: String?, time: String?, author: String?) : String {
            var value = ""
            if (date != null && time != null && author != null) {
                value = "$date às $time por $author"
            }
            return value
        }

        fun formatHtmlText(text: String?) : Spanned {
            var spanned: Spanned = SpannedString("")
            text?.let { itText ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    spanned = Html.fromHtml(itText, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    spanned = Html.fromHtml(itText)
                }
            }
            return spanned
        }
    }
}
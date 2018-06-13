package io.jffiorillo.venezuelanews.model

import android.os.Parcelable
import com.squareup.moshi.Json
import io.jffiorillo.venezuelanews.utils.isNotNullAndNotEmpty
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime

@Parcelize
data class Article(
  @Json(name = "ar_id") val id: String? = "", //116991504
  @Json(name = "ar_titulo") val title: String? = "", //Trump denunció “corrupción” dentro del FBI y restó valor a investigación rusa
  @Json(name = "ar_link") val link: String? = "", //http://gunow.azurewebsites.net/article/Trump-denuncio-corrupcion-dentro-del-FBI-y-resto-valor-a-investigacion-rusa-Caraota-Digital
  @Json(name = "ar_slug") val slug: String? = "", //Trump-denuncio-corrupcion-dentro-del-FBI-y-resto-valor-a-investigacion-rusa-Caraota-Digital
  @Json(name = "ar_fecha_articulo") val date: LocalDateTime?, //2018-03-18 04:33:55
  @Json(name = "ar_link_fuente") val sourceLink: String? = "", //http://www.caraotadigital.net/internacionales/trump-denuncio-corrupcion-dentro-del-fbi-y-resto-valor-investigacion-rusa/
  @Json(name = "fu_id") val sourceId: String? = "", //81
  @Json(name = "fu_nombre") val sourceName: String? = "", //Caraota Digital
  @Json(name = "fu_pais") val sourceCountryCode: String? = "", //ve
  @Json(name = "fu_idioma") val sourceLanguage: String? = "", //es
  @Json(name = "fu_color_1") val sourceColor: String? = "", //2
  @Json(name = "fu_url_principal") val sourceUrl: String? = "", //http://caraotadigital.net
  @Json(name = "fu_url_logo_mini") val sourceUrlLogoMini: String? = "", //http://gunow.azurewebsites.net/api-noticias/assets/logos/mini/caraota.png
  @Json(name = "fu_url_logo_300") val sourceUrlLogo300: String? = "", //http://gunow.azurewebsites.net/api-noticias/assets/logos/300/caraota.png
  @Json(name = "fu_url_logo_500") val sourceUrlLogo500: String? = "", //http://gunow.azurewebsites.net/api-noticias/assets/logos/500/caraota.png
  @Json(name = "fu_url_logo_normal") val sourceUrlLogoNormal: String? = "", //http://gunow.azurewebsites.net/api-noticias/assets/logos/normal/caraota.png
  @Json(name = "imagen_extraida") val imageUrl: String? = ""
) : Parcelable {
  fun genId(pos: String = "defaultTransitionName"): String {
    return if (title.isNotNullAndNotEmpty()) {
      title!!
    } else if (id.isNotNullAndNotEmpty()) {
      id!!
    } else {
      pos
    }
  }
}
package io.jffiorillo.venezuelanews.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("ar_id") val id: String, //116991504
    @SerializedName("ar_titulo") val title: String, //Trump denunció “corrupción” dentro del FBI y restó valor a investigación rusa
    @SerializedName("ar_link") val link: String, //http://gunow.azurewebsites.net/article/Trump-denuncio-corrupcion-dentro-del-FBI-y-resto-valor-a-investigacion-rusa-Caraota-Digital
    @SerializedName("ar_slug") val slug: String, //Trump-denuncio-corrupcion-dentro-del-FBI-y-resto-valor-a-investigacion-rusa-Caraota-Digital
    @SerializedName("ar_fecha_articulo") val date: String, //2018-03-18 04:33:55
    @SerializedName("ar_link_fuente") val sourceLink: String, //http://www.caraotadigital.net/internacionales/trump-denuncio-corrupcion-dentro-del-fbi-y-resto-valor-investigacion-rusa/
    @SerializedName("fu_id") val sourceId: String, //81
    @SerializedName("fu_nombre") val sourceName: String, //Caraota Digital
    @SerializedName("fu_pais") val sourceCountryCode: String, //ve
    @SerializedName("fu_idioma") val sourceLanguage: String, //es
    @SerializedName("fu_color_1") val sourceColor: String, //2
    @SerializedName("fu_url_principal") val sourceUrl: String, //http://caraotadigital.net
    @SerializedName("fu_url_logo_mini") val sourceUrlLogoMini: String, //http://gunow.azurewebsites.net/api-noticias/assets/logos/mini/caraota.png
    @SerializedName("fu_url_logo_300") val sourceUrlLogo300: String, //http://gunow.azurewebsites.net/api-noticias/assets/logos/300/caraota.png
    @SerializedName("fu_url_logo_500") val sourceUrlLogo500: String, //http://gunow.azurewebsites.net/api-noticias/assets/logos/500/caraota.png
    @SerializedName("fu_url_logo_normal") val sourceUrlLogoNormal: String //http://gunow.azurewebsites.net/api-noticias/assets/logos/normal/caraota.png
)
package com.google.samples.apps.sunflower.plantdetail

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.accompanist.themeadapter.material.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    val plant by plantDetailViewModel.plant.observeAsState()

    plant?.let{
        PlantDetailContent(it)
    }
}
@Composable
private fun PlantName(name: String){
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            /**
             * fillMaxWidth() é utilizado para ocupar o valor máximo de largura disponivel,
             * corresponde ao valor "match_parent" do "layout_width" no XML.
             */
            .fillMaxWidth()
            /**
             * padding() é usado para que o preenchimento horizontal margin_small seja aplicado,
             * corresponde ao marginStart e marginEnd no XML.
             */
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            /**
             * wrapContentWidth() é usado para alinhar o texto de modo que ele fique centralizado
             * horizontalmente, modificador semelhante ao gravity de center_horizontal no XML.
             */
            .wrapContentWidth(Alignment.CenterHorizontally)
    )

}

@Preview
@Composable
private fun PlantNamePreview(){
    MdcTheme {
        PlantName(name = "Apple")
    }
}

@Composable
private fun PlantDescription(description: String){
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
    /**
     * AndroidView é responsavel por construir views.
     */
    AndroidView(
        /**
         * lambda onde o TextView vai ser fabricado
         */
        factory = {context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )
}

@Composable
@Preview
private fun PlantDescriptionPreview(){
    MdcTheme {
        PlantDescription(description = "HTML<br><br>description")
    }
}

@Composable
private fun PlantWatering(wateringInterval: Int){
    Column(Modifier.fillMaxWidth()) {

        /**
         * Como é compartilhado com mais Texts o padding horizontal e o alinhamento, foram criadas
         * essas variaveis locais do modificador para serem possiveis reutilizar.
         */
        val centerWidthPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)
        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWidthPaddingModifier.padding(top = normalPadding)
        )

        val wateringIntervalText = pluralStringResource(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        Text(
            text = wateringIntervalText,
            modifier = centerWidthPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

@Preview
@Composable
private fun PlantWateringPreview(){
    MdcTheme{
        PlantWatering(wateringInterval = 7)
    }
}


@Composable
fun PlantDetailContent(plant: Plant){
    Surface {
        Column(Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(plant.name)
            PlantWatering(plant.wateringInterval)
            PlantDescription(plant.description)
        }
    }
}

@Preview
@Composable
private fun PlantDetailContentPreview(){
    val plant = Plant("id", "Apple", "HTML<br><br>description",3,30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}


package com.google.samples.apps.sunflower.plantdetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun PlantDetailContent(plant: Plant){
    PlantName(plant.name)
}

@Preview
@Composable
private fun PlantDetailContentPreview(){
    val plant = Plant("id", "Apple", "description",3,30, "")
    MaterialTheme {
        PlantDetailContent(plant)
    }
}


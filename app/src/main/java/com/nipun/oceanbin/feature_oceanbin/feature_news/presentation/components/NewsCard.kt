package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.BigSpacing
import com.nipun.oceanbin.ui.theme.ExtraBigSpacing
import com.nipun.oceanbin.ui.theme.ExtraSmallSpacing
import com.nipun.oceanbin.ui.theme.MediumSpacing

@Composable
fun NewsCard(
    modifier : Modifier = Modifier,
    image : Int,
    heading : String,
    description : String,
    newsSource : String
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(MediumSpacing)
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(
                id = image),
                contentDescription = heading,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(2.5f)
                    .clip(RoundedCornerShape(MediumSpacing, MediumSpacing, 0.dp, 0.dp)),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = modifier
                    .fillMaxWidth(),
                elevation = ExtraSmallSpacing
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(MediumSpacing)
                ) {
                    Text(
                        text = heading,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(MediumSpacing))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(MediumSpacing)
            ) {
                Text(text = "Source:")
                Spacer(modifier = Modifier.size(MediumSpacing))
                Text(text = newsSource)
            }

        }
    }
}
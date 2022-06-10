@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection")

package binar.ganda.challengechapter8.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import binar.ganda.challengechapter8.model.MovieListItem
import binar.ganda.challengechapter8.ui.theme.ChallengeChapter8Theme
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Detail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val detail = intent.getSerializableExtra("detail") as MovieListItem
                    DisplayDetail(detail)
                }
            }
        }
    }
}

@Composable
fun DisplayDetail(movie : MovieListItem) {
    val posterBase = "https://image.tmdb.org/t/p/w500/"
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val newsDetail =

            Image(
                painter = rememberImagePainter(data = posterBase+ movie.posterPath),
                contentDescription = "img",
                modifier = Modifier.size(400.dp)
            )
        Text(
            text = movie.title,
            modifier = Modifier.padding(bottom = 5.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Text(
            text = movie.releaseDate,
            modifier = Modifier.padding(bottom = 5.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Text(
            text = movie.popularity.toString(),
            modifier = Modifier.padding(bottom = 5.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )

        Text(
            text = movie.overview,
            modifier = Modifier.padding(bottom = 5.dp),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview4() {
    ChallengeChapter8Theme {
        DisplayDetail(
            movie = MovieListItem(
                "overview",
                "title",
                "posterpath",
                "backdroppath",
                "release date",
                1232.3,
                7.89,
                32,
                1089
            )
        )
    }
}
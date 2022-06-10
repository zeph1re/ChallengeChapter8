package binar.ganda.challengechapter8.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import binar.ganda.challengechapter8.R
import binar.ganda.challengechapter8.datastore.UserManager
import binar.ganda.challengechapter8.model.MovieListItem
import binar.ganda.challengechapter8.ui.theme.ChallengeChapter8Theme
import binar.ganda.challengechapter8.viewmodel.MovieViewModel
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mContext = LocalContext.current
                    val movieViewModel = viewModel(modelClass = MovieViewModel::class.java)
                    val dataMovie by movieViewModel.dataMovieState.collectAsState()
                    val userManager = UserManager(mContext)

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painterResource(id = R.drawable.ic_launcher_background),
                                modifier = Modifier.size(30.dp),
                                contentDescription = "",
                            )
                            Text(
                                text = "LOGOUT",
                                color = Color.Black,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        GlobalScope.launch {
                                            userManager.clearData()
                                        }
                                        mContext.startActivity(Intent(mContext, Login::class.java))
                                    }
                            )
                        }

                        LazyColumn {
                            if (dataMovie.isEmpty()) {
                                item {
                                }
                            }

                            items(dataMovie) {
                                MovieCard(movie = it)
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: MovieListItem) {
    val posterBase = "https://image.tmdb.org/t/p/w500/"
    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        val mContext = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(mContext, Detail::class.java)
                    intent.putExtra("detail", movie)
                    mContext.startActivity(intent)
                },
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row {
                Image(
                    painter = rememberImagePainter(data = posterBase + movie.posterPath),
                    contentDescription = "img",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(100.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier.padding(bottom = 5.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.releaseDate,
                        modifier = Modifier.padding(bottom = 5.dp),
                        fontSize = 13.sp,
                    )
                    Text(
                        text = movie.popularity.toString(),
                        modifier = Modifier.padding(bottom = 5.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMovieCard() {
    ChallengeChapter8Theme {
        MovieCard(
            MovieListItem(
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




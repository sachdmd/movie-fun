package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.albums.Album;
import org.superbiz.moviefun.albums.AlbumFixtures;
import org.superbiz.moviefun.albums.AlbumsBean;
import org.superbiz.moviefun.movies.Movie;
import org.superbiz.moviefun.movies.MovieFixtures;
import org.superbiz.moviefun.movies.MoviesBean;

import java.util.Map;

@Controller
public class HomeController {

    private final MoviesBean moviesBean;
    private final AlbumsBean albumsBean;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    private  TransactionTemplate transactionTemplate;

   //@Qualifier("moviesTransactionManager")
    private PlatformTransactionManager transactionManagerForMovies;

    //@Qualifier("albumsTransactionManager")
    private  PlatformTransactionManager transactionManagerForAlbums;


    public HomeController(MoviesBean moviesBean, AlbumsBean albumsBean, MovieFixtures movieFixtures, AlbumFixtures albumFixtures,PlatformTransactionManager transactionManagerForMovies,PlatformTransactionManager transactionManagerForAlbums) {
        this.moviesBean = moviesBean;
        this.albumsBean = albumsBean;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
        this.transactionManagerForMovies=transactionManagerForMovies;
        this.transactionManagerForAlbums=transactionManagerForAlbums;
    }



    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        addMovie();
        addAlbum();
        model.put("movies", moviesBean.getMovies());
        model.put("albums", albumsBean.getAlbums());

        return "setup";
    }


    private void addMovie(){

        new TransactionTemplate(transactionManagerForMovies).execute(status->
                {
                    for (Movie movie : movieFixtures.load()) {
                        moviesBean.addMovie(movie);
                    }
                    return null;
                }
        );
    }


    private void addAlbum(){
        new TransactionTemplate(transactionManagerForAlbums).execute(status->
        {
            for (Album album : albumFixtures.load()) {
            albumsBean.addAlbum(album);
        }
            return null;
        }
        );

    }


}

package pawan.test.samsungtest;

public class MovieData  {

    private String title, popularity, image_url, genre;

    public MovieData(String title, String popularity, String image_url, String genre) {
        this.title = title;
        this.popularity = popularity;
        this.image_url = image_url;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getGenre() {
        return genre;
    }
}

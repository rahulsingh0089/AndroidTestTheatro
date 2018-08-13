package com.example.rsingh7.androidtesttheatro.model;

public class Movie {
	private static String thumbnailUrl;
    private String title;
    private String imdbid;
    private String releasedYear;
    private String rating;
    private String actor;

	public Movie() {
	}

	public Movie(String title, String thumbnailUrl, String releasedYear, String rating, String actor, String imdbid) {
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
		this.releasedYear = releasedYear;
		this.rating = rating;
		this.actor = actor;
		this.imdbid = imdbid;
	}

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getYear() {
		return releasedYear;
	}

	public void setYear(String releasedYear) {
		this.releasedYear = releasedYear;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return actor;
	}

	public void setGenre(String actor) {
		this.actor = actor;
	}

}

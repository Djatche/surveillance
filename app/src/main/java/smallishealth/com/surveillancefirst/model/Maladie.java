package smallishealth.com.surveillancefirst.model;

/**
 * Created by hp on 10/04/2018.
 */

public class Maladie {
    private String name_fr;
    private String name_en;
    private String definitionDesCasFr;
    private String definitionDesCasEn;
    private int imagePathologie;
    private int moviePathologie;



    public Maladie(String name_fr, String name_en, String definitionDesCasFr, String definitionDesCasEn) {
        this.name_fr = name_fr;
        this.name_en = name_en;
        this.definitionDesCasFr = definitionDesCasFr;
        this.definitionDesCasEn = definitionDesCasEn;
    }

    public Maladie(String name_fr, String name_en, String definitionDesCasFr, String definitionDesCasEn, int imagePathologie) {
        this.name_fr = name_fr;
        this.name_en = name_en;
        this.definitionDesCasFr = definitionDesCasFr;
        this.definitionDesCasEn = definitionDesCasEn;
        this.imagePathologie = imagePathologie;
    }

    public Maladie(String name_fr, String name_en, int imagePathologie) {
        this.name_fr = name_fr;
        this.name_en = name_en;
        this.imagePathologie = imagePathologie;
    }

    public Maladie(String name_fr, String name_en, String definitionDesCasFr, String definitionDesCasEn, int imagePathologie, int moviePathologie) {
        this.name_fr = name_fr;
        this.name_en = name_en;
        this.definitionDesCasFr = definitionDesCasFr;
        this.definitionDesCasEn = definitionDesCasEn;
        this.imagePathologie = imagePathologie;
        this.moviePathologie = moviePathologie;
    }

    public String getName_fr() {
        return name_fr;
    }

    public String getName_en() {
        return name_en;
    }

    public String getDefinitionDesCasFr() {
        return definitionDesCasFr;
    }

    public String getDefinitionDesCasEn() {
        return definitionDesCasEn;
    }

    public int getImagePathologie() {
        return imagePathologie;
    }

    public int getMoviePathologie() {
        return moviePathologie;
    }
}

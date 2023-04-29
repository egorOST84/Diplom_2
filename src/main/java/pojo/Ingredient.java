package pojo;
public class Ingredient {
    private String _id;
    private String name;
    private String type;
    private Long proteins;
    private Long fat;
    private Long carbohydrates;
    private Long calories;
    private Long price;
    private String image;
    private String imageMobile;
    private String imageLarge;

    public Ingredient() {
    }

    public Ingredient(String id, String name, String type, Long proteins, Long fat, Long carbohydrates, Long calories, Long price, String image, String imageMobile, String imageLarge, Long v) {
        super();
        this._id = id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.price = price;
        this.image = image;
        this.imageMobile = imageMobile;
        this.imageLarge = imageLarge;
    }

    public String getId() {
        return _id;
    }

    public Ingredient setId(String id) {
        this._id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Ingredient setType(String type) {
        this.type = type;
        return this;
    }

    public Long getProteins() {
        return proteins;
    }

    public Ingredient setProteins(Long proteins) {
        this.proteins = proteins;
        return this;
    }

    public Long getFat() {
        return fat;
    }

    public Ingredient setFat(Long fat) {
        this.fat = fat;
        return this;
    }

    public Long getCarbohydrates() {
        return carbohydrates;
    }

    public Ingredient setCarbohydrates(Long carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    public Long getCalories() {
        return calories;
    }

    public Ingredient setCalories(Long calories) {
        this.calories = calories;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public Ingredient setPrice(Long price) {
        this.price = price;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Ingredient setImage(String image) {
        this.image = image;
        return this;
    }

    public String getImageMobile() {
        return imageMobile;
    }

    public Ingredient setImageMobile(String imageMobile) {
        this.imageMobile = imageMobile;
        return this;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public Ingredient setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
        return this;
    }
}
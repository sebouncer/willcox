package nz.co.willcox.photo;

public class Main {

    private ImageConverter imageConverter;

    public Main(ImageConverter imageConverter) {
        this.imageConverter = imageConverter;
    }

    public static void main(String[] args) {
        ImageConverter imageConverter = new ImageConverter();
        Main main = new Main(imageConverter);
        main.makeThumbnails();
    }

    private void makeThumbnails() {

        String input = "E:\\workspace\\WeddingPhotos\\1_Stephanie & Andrew Sneak Peek_222442_ Full Size\\Stephanie-Andrew-Sneak-Peek-1.jpg";
        String output = "E:\\workspace\\WeddingPhotos\\1_Stephanie & Andrew Sneak Peek_222442_ Full Size\\Stephanie-Andrew-Sneak-Peek-1-thumbnail.jpg";
        imageConverter.createThumbnailCrop(input, output);
    }
}

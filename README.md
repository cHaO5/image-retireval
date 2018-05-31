# Image Retrieval

This is a JavaFx application implementing image retrieval based on Perceptual Image Hashing Algorithm and Hamming Distance .

With this application, users can choose images from image library and retrieve similar images. Features like identifying the label of images and advanced filter are supported. 

## Interface

![img](https://github.com/cHaO5/image-retireval/raw/master/images/main.jpg)

1. Icon.
2. 'Choose Image' button. Press to choose the image to be retrieved.
3. 'Search' button. Press and start to retrieve.
4. Search parameters. Offer three parameters(size, number of results and catalog) to get specific type of results and limit the amount of images.

![img](https://github.com/cHaO5/image-retireval/raw/master/images/search.jpg)

5. Review of query image.
6. Overview of results.
7. Label of image.
8. Result. Display 6 results per page.
9. Previous page and next page.

## Usage Instruction

1. Run application in command.

   ```powershell
   java -jar image-retireval-1.0-SNAPSHOT-jfx.jar
   ```

2. Choose the image. Images are under folder  `/img` .

3. Choose advanced search parameters.(optional) * \*\*

4. Press 'Search' button to retrieve images.\*\*\*

5. Check the results.

\* Size: 'All' means query all size of images; 'Small' means the image is small than 300px\*200px; 'Medium' means the image is between 300px\*200px and 400px\*200px; 'Large' means the image is between 400px\*200px and 300px\*300px.

\*\* If user chooses advenced parameters after searching, please press 'Search' button again.

\*\*\* Searching may take some time. Please wait patiently.

## Five-Stage Framework

- Formulation: User can simply search image and if they want some specific results, they can search with advanced parameters such as size, number of results and catalog. Such options can increase the accuracy of retrieval. 
- Initiation of action: 'Choose Image' button and Search' button are offered togerther so users can easily complete choose-search actions. Considering that advanced options are not necessary and more related to result of images, those options are placed above the results.

- Review of results: To offer more imfomation of retrieved images, mu application not only display results of query, but show overview of the results and label detected from image as well.
- Refinement: User can change advanced parameters to sift results to conveniently focus more on meaningful information or reduce the wrong results. Label of image is also a suggestion for users.
- Use: Functions like allowing users to add their favourite images to list or make some comments on images can raise user experiment but are not contained in this application.

## Perceptual Image Hashing Algorithm

A perceptual hash is a fingerprint based on the image input, that can be used to compare images by calculating the Hamming Distance and I use Average Hash in this application.

The procedure is as follows:

1. Reduce size. To remove high frequencies and details of images, images are resized to smaller one, which are resulting pixels.
2. Reduce the color. Converting the images to grayscale and the images leave some color values.
3. Calculate the average color. Calculate with previous values.
4. Calculate the hash. The final fingerprint is calculated based on whether a pixel is brighter or darker than average grayscale value.

After we get the perceptual hashes for images, Hamming distance is used in order to compare them. The higher this distance, the lower the change of indentical or similar images. When the similarity is 1.0, we can tell that these two images pobably the same one.
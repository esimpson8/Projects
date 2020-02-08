The data comes already in an 80/20 train/test split, and for simplicity's sake I left it in this format. In the training set, there are 70,295 rgb images that are each 256x256. 
These are split up into 38 categories, comprising multiple different fruits and vegetables, with healthy and unhealthy versions of each. Each image is of a single leaf of the 
given specimen, with nothing of interest in the background. Some of the leaves take up the entire picture, to the extent that one can't even determine it is a leaf. Furthermore, 
some of the samples from some of the classes are difficult to distinguish side by side even to me. Then again, I have not been trained on thousands of examples. 

This project centered around issues related to the size of the data. I tried implementing the shallow approach first, going with a Gaussian Naive Bayes because this had worked 
well for me before. Right away I encountered memory errors, so I tried splitting the data up into segments and training the model over batches at a time. Scikit learn has a 
function for this, partial_fit. However, in order to use this properly, I would have needed to write my own minibatch iterator. Combining the facts that I did not want to spend 
too much time doing that, and I knew keras has a built in iterator, and I made the decision to try a simple dense neural network as the baseline instead. This also ran into 
issues, as even with keras's built in ImageDataGenerator, I could not get the data shaped right for the network to be happy. Curiously, I then tried a basic convolutional network 
and it worked perfectly. The first basic convolutional network I tried had a minibatch size of 512, and 3 convolutional layers with 16, 32, and 64 3x3 filters respectively. 
At first, this obtained an accuracy of 80% but crashed due to memory constraints before it got to 5 epochs. I reduced the batch size to 128, switched to GPU processing, and was 
able to obtain 92% validation accuracy on the fifth epoch. With an increased number of filters in the convolutional layers, I was able to obtain 94% accuracy with this network. 

Then I tried implementing a custom inception-like network. I wanted to implement an ensemble model. This network had one branch with 1x1 and 3x3 convolutional networks, one 
branch with 1x1 and 5x5 convolutional networks, and one branch with a max pooling layer followed by a 1x1 convolutional network. All the convolutional networks had 64 filters. 
These were then flattened, concatenated, and with a dense softmax layer at the end. Unfortunately, this network could not run no matter how I tweaked the parameters, because 
it ran into resource exhausted errors every time. Even with a batch size of 1, and with only 2 branches, the network still was too large to run on my (relatively new and 
powerful) desktop GPU. 

With a custom ensemble out, I tried finetuning a pretrained inception module base. My first attempt at this used an inceptionv3 base, with a global pooling layer on top, 
followed by a 128 unit dense layer, and finally the softmax output. Unfortunately I did not keep track of validation accuracy during training of the top layers, so when I 
unfroze the top inception module, I obtained a bad validation accuracy (55%) and the model was clearly overfitting. To combat this, I increased the top dense layers to 512 units, 
and trained the top layers for longer. This almost increased the accuracy to 60%, but even after 20 epochs the model still showed clear signs of overfitting, and was getting 
lackluster results. This is why the final model is displayed after only 5 epochs. 

Overall, I am somewhat disappointed with the results - I tried 6 different models, only one of which achieved decent results. This has certainly been a valuable experience in 
terms of the challenges of dealing with larger datasets though, even though this dataset is still tiny compared to real life ones. My best result, curiously enough, came from 
the basic convolutional network architecture. I think one contributing issue with the inception network may be the fact that it was pretrained on ImageNet, which contains images 
of primarily objects that are not leaf like. If I had more time I would train on a base that is trained on more leaflike objects, such as perhaps flowers. I ran out of time to 
do this, however, and all of the pretrained bases that I saw in the keras documentation are pretrained on ImageNet as well. I would very much like to see how the custom ensemble 
performs as well - the model itself works just fine, I simply do not have enough computing power for it. That is why I have left it in the file despite it not working for me. 
Overall, this dataset is more challenging than it appears - perhaps partly because of the similarity of a lot of the images contained in it. Nonetheless, the convolutional 
baseline seemed to work pretty well and pretty fast on it. 
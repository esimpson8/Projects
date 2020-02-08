Step 1. There are four classes of data represented, with 900 each of them in the training data (901 for sparse traffic), and 200 of each class in the test data. Looking over the
data, it appears that many of the data for "accidents" contain people standing next to the cars, which is true of both the training and the test data. This is probably fine, 
since it is reflected in both, but it may lead our model to falsely believe that people next to cars is indicative of an accident, when this may not be the case. Additionally,
the images are not of uniform size. In the y dimension, the minimum is 109 and the maximum is 303, and in the x dimension, the min is 166 and the max is 461. 

Step 2: Highest training accuracy: 59%, Highest test accuracy: 55.6%. This was achieved with 3 layers of 16 hidden units. I am surprised that this wasn't higher, but didn't do 
a ton to optimize it; in particular, I didn't make any attempts at regularization or data augmentation, as I will focus more on the convolutional network and implement those 
there. Perhaps the somewhat low amount of data (less than 1000 examples per class) affected this result. Perhaps the way in which I flattened the data also has something to 
do with it; it took me a long time to figure out how to flatten the data into the right shape so that the network would accept it. 

Step 3: On the first attempt with a convolutional network, I achieved a test accuracy of 72.6%, which is better, but leaves a lot to be desired. On the second attempt, I used 
the same model but with 10 epochs instead of 5, and with a dropout layer set to 0.5.  This, however, only had a test accuracy of 72%, and took several hours to run. I was 
unable to figure out how to use data augmentation for this, as I could not figure out how to use it to shape the data correctly for the network. At least the network was not 
overfitting at 10 epochs - but it is hard to see how the parameters could be improved upon. I tried one more version of this network, running overnight, with 3 conv layers and 
3 max pooling layers, and with 2 hidden dense layers and a dropout layer. This ran overnight, but only resulted in 64% accuracy. 

Step 4: The results definitely improved drastically when I started using convolutional networks, however they still weren't stellar, and testing hyperparameters became extremely 
difficult with convnets. It took several hours to train the network each time I wanted to test a new architecture, so I had to be a lot smarter about which parameters I tweaked, 
and in the end I still couldn't get a great accuracy out of it. I think not being able to properly implement data augmentation was a big hampering factor, and if I had had more 
time, this would have helped a lot. The main thing I learned is the incredible amount of time spent on shaping the data properly - most of the time I spent on this project was 
spent on trying to figure out how best to unify the sizes of the images and then shape the input data so as to fit the network. I had not realized how much time this would take, 
nor how long it would take to run the trials each time once the conv layers were in place. 
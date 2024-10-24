const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

exports.sendNotificationToAllCustomers = functions.pubsub.schedule('31 0 17 1 *').timeZone('Asia/Kolkata')
    .onRun((context) => {
        // Manually set the trial user data
        const trialUserId = 'XRq3mtwSTwWwF5nrtk9FNSHAjiW2';
        const trialMobileno = '+916396572084'; // Adding +91 to the provided number

        const registrationToken = trialMobileno; // Use the mobile number as the FCM registration token

        const message = {
            data: {
                title: 'Your App Name',
                body: 'Hello! Your custom notification message here.',
            },
            token: registrationToken,
        };

        // Send a push notification to the trial customer using the trial mobile number
        return admin.messaging().send(message)
            .then((response) => {
                console.log('Successfully sent notification:', response);
                return null;
            })
            .catch((error) => {
                console.error('Error sending notification:', error);
                return null;
            });
    });


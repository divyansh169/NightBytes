const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendOrderNotification = functions.https.onCall(async (data, context) => {
    const { chefId, message } = data;

    // Fetch the chef's device token from the database
    const chefTokenRef = admin.database().ref(`ChefTokens/${chefId}`);
    const chefTokenSnapshot = await chefTokenRef.once('value');
    const chefToken = chefTokenSnapshot.val();

    if (!chefToken) {
        throw new functions.https.HttpsError('not-found', 'Device token not found for the specified chef.');
    }

    // Create the notification payload
    const payload = {
        notification: {
            title: 'New Order',
            body: message
        },
        token: chefToken
    };

    try {
        await admin.messaging().send(payload);
        return { success: true };
    } catch (error) {
        throw new functions.https.HttpsError('unknown', 'Failed to send notification', error);
    }
});

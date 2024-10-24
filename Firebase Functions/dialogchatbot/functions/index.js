// const functions = require('firebase-functions');
// const admin = require('firebase-admin');

// admin.initializeApp();

// exports.showMenu = functions.https.onRequest(async (req, res) => {
//   try {
//     // Retrieve address data from Firebase Realtime Database
//     const snapshot = await admin.database().ref('/address').once('value');
//     const address = snapshot.val();

//     // Construct response object
//     const response = {
//       fulfillmentText: `Our address is ${address}.`, // Send the address back as part of the response
//     };

//     // Send response to Dialogflow
//     res.json(response);
//   } catch (error) {
//     console.error('Error fetching address from Firebase:', error);
//     res.status(500).send('Error fetching address from Firebase');
//   }
// });



// const functions = require('firebase-functions');
// const admin = require('firebase-admin');

// admin.initializeApp();

// exports.showMenu = functions.https.onRequest(async (req, res) => {
//   try {
//     // Retrieve address data from Firebase Realtime Database
//     const snapshot = await admin.database().ref('/FoodSupplyDetails/Rajasthan/Jaipur/Campus Parlour/sK5Sa98ELDP6YBdHO91WRsofDQV2/0212abe9-55bf-496f-984f-8fa8cdf3fe11/Dishes').once('value');
//     const menu = snapshot.val();

//     // Construct response object
//     const response = {
//       fulfillmentText: `Our menu is ${menu}.`, // Send the address back as part of the response
//     };

//     // Send response to Dialogflow
//     res.json(response);
//   } catch (error) {
//     console.error('Error fetching address from Firebase:', error);
//     res.status(500).send('Error fetching address from Firebase');
//   }
// });


// const functions = require('firebase-functions');
// const admin = require('firebase-admin');

// admin.initializeApp();

// exports.showMenu = functions.https.onRequest(async (req, res) => {
//   try {
//     // Retrieve menu data from Firebase Realtime Database
//     const snapshot = await admin.database().ref('/FoodSupplyDetails/Rajasthan/Jaipur/Campus Parlour/sK5Sa98ELDP6YBdHO91WRsofDQV2').once('value');
//     const menu = snapshot.val();

//     // Extract "Dishes" value for all dish IDs
//     const dishes = {};
//     for (const dishId in menu) {
//       dishes[dishId] = menu[dishId].Dishes;
//     }

//     // Construct response object
//     let menuText = "Menu:\n";
//     for (const dishId in dishes) {
//       const dish = dishes[dishId];
//       menuText += `- ${dish}\n`;
//     }
    
//     // Send response to Dialogflow
//     const response = {
//       fulfillmentText: menuText,
//     };
//     res.json(response);
//   } catch (error) {
//     console.error('Error fetching menu from Firebase:', error);
//     res.status(500).send('Error fetching menu from Firebase');
//   }
// });



//..................working fine


// const functions = require('firebase-functions');
// const admin = require('firebase-admin');

// admin.initializeApp();

// exports.showMenu = functions.https.onRequest(async (req, res) => {
//   try {
//     // Retrieve menu data from Firebase Realtime Database
//     const snapshot = await admin.database().ref('/FoodSupplyDetails/Rajasthan/Jaipur/Campus Parlour/sK5Sa98ELDP6YBdHO91WRsofDQV2').once('value');
//     const menu = snapshot.val();

//     // Extract "Dishes" value for all dish IDs
//     const dishes = {};
//     for (const dishId in menu) {
//       dishes[dishId] = menu[dishId].Dishes;
//     }

//     // Construct response object
//     let menuText = "Menu:\n";
//     for (const dishId in dishes) {
//       const dish = dishes[dishId];
//       const price = menu[dishId].Price; // Assuming there's a "Price" field in your database
//       menuText += `- ${dish}: Rs. ${price}\n`; // Append each dish with its price on a new line
//     }
    
//     // Send response to Dialogflow
//     const response = {
//       fulfillmentText: menuText,
//     };
//     res.json(response);
//   } catch (error) {
//     console.error('Error fetching menu from Firebase:', error);
//     res.status(500).send('Error fetching menu from Firebase');
//   }
// });

//.........................................

//completely working newest
// const functions = require('firebase-functions');
// const admin = require('firebase-admin');

// admin.initializeApp();

// exports.showMenu = functions.https.onRequest(async (req, res) => {
//   try {
//     // Retrieve menu data from Firebase Realtime Database
//     const snapshot = await admin.database().ref('/FoodSupplyDetails/Rajasthan/Jaipur/Campus Parlour/sK5Sa98ELDP6YBdHO91WRsofDQV2').once('value');
//     const menu = snapshot.val();

//     // Construct response object
//     let menuText = "Menu:\n";
//     for (const dishId in menu) {
//       const dish = menu[dishId];
      
//       // Check if Stockcnt is not "0"
//       if (dish.Stockcnt !== "0") {
//         const price = dish.Price;
//         menuText += `- ${dish.Dishes}: Rs. ${price}\n`; // Append each dish with its price on a new line
//       }
//     }
    
//     // Send response to Dialogflow
//     const response = {
//       fulfillmentText: menuText,
//     };
//     res.json(response);
//   } catch (error) {
//     console.error('Error fetching menu from Firebase:', error);
//     res.status(500).send('Error fetching menu from Firebase');
//   }
// });

/////////////////////////////////////////////////////////////////////////////////////////

const functions = require('firebase-functions');
const { WebhookClient } = require('dialogflow-fulfillment');

exports.showMenu = functions.https.onRequest((request, response) => {
  const agent = new WebhookClient({ request, response });

  function nightbytesHandler(agent) {
    const userId = request.body.queryResult.parameters.userid;
    const chefId = request.body.queryResult.parameters.chefid;

    agent.add(`User ID: ${userId}, Chef ID: ${chefId}`);
  }

  let intentMap = new Map();
  intentMap.set('nightbytes', nightbytesHandler);

  agent.handleRequest(intentMap);
});








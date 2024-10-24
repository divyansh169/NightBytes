// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetCounter = functions.pubsub.schedule('every day 04:00').timeZone('Asia/Kolkata').onRun((context) => {
//   const database = admin.database();

//   // Reset the cunt counter to zero
//   return database.ref('/ChefOrderCounter/cunt').set("0");
// });

// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCounters = functions.pubsub.schedule('every day 04:00').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Get a snapshot of all chefIds
//   const snapshot = await chefOrderCounterRef.once('value');

//   // Iterate through each chefId
//   snapshot.forEach((chefSnapshot) => {
//     const chefId = chefSnapshot.key;

//     // Reset the cunt for each chef to "0"
//     chefOrderCounterRef.child(`${chefId}/cunt`).set("0");
//   });

//   return null;
// });


// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 04:00').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Get a snapshot of all chefIds in ChefOrderCounter
//   const counterSnapshot = await chefOrderCounterRef.once('value');

//   // Iterate through each chefId
//   counterSnapshot.forEach(async (counterSnapshot) => {
//     const chefId = counterSnapshot.key;

//     // Reset the cunt for each chef to "0"
//     await chefOrderCounterRef.child(`${chefId}/cunt`).set("0");

//     // Get the daily cost value
//     const dailyCostSnapshot = await chefDailyCostRef.child('cost').once('value');
//     const dailyCost = dailyCostSnapshot.val();

//     // Get the current wallet value for the chef
//     const currentWalletSnapshot = await chefWalletRef.child(`${chefId}/wal`).once('value');
//     const currentWalletValue = currentWalletSnapshot.val();

//     // Calculate the new wallet value by decrementing the daily cost
//     const newWalletValue = Math.max(0, currentWalletValue - dailyCost);

//     // Update the wallet value for the chef
//     await chefWalletRef.child(`${chefId}/wal`).set(newWalletValue);
//   });

//   return null;
// });











// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 20:32').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   // Get a snapshot of all chefIds in ChefOrderCounter
//   const counterSnapshot = await chefOrderCounterRef.once('value');

//   // Iterate through each chefId
//   counterSnapshot.forEach(async (counterSnapshot) => {
//     const chefId = counterSnapshot.key;

//     // Reset the cunt for each chef to "0"
//     await chefOrderCounterRef.child(`${chefId}/cunt`).set("0");

//     // Get the daily cost value
//     const dailyCostSnapshot = await chefDailyCostRef.child('cost').once('value');
//     const dailyCost = dailyCostSnapshot.val();

//     // Get the current wallet value for the chef
//     const currentWalletSnapshot = await chefWalletRef.child(`${chefId}/wal`).once('value');
//     const currentWalletValue = currentWalletSnapshot.val();

//     // Calculate the new wallet value by decrementing the daily cost
//     const newWalletValue = Math.max(0, currentWalletValue - dailyCost);

//     // Update the wallet value for the chef
//     await chefWalletRef.child(`${chefId}/wal`).set(newWalletValue);

//     // Check if the new wallet value is less than or equal to zero
//     if (newWalletValue <= 0) {
//       // Set the value of ChefStopped/chefid/stopped to one
//       await chefStoppedRef.child(`${chefId}/stopped`).set(1);
//     } else {
//       // Set the value of ChefStopped/chefid/stopped to zero
//       await chefStoppedRef.child(`${chefId}/stopped`).set(0);
//     }
//   });

//   return null;
// });





// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 00:15').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "0"
//     await chefOrderCounterRef.once('value', snapshot => {
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         chefOrderCounterRef.child(`${chefId}/cunt`).set("0");
//       });
//     });

//     // Get the daily cost value
//     const dailyCostSnapshot = await chefDailyCostRef.child('cost').once('value');
//     const dailyCost = dailyCostSnapshot.val();

//     // Get a snapshot of all chefIds in ChefWallet
//     const walletSnapshot = await chefWalletRef.once('value');

//     // Iterate through each chefId
//     walletSnapshot.forEach(async (chefSnapshot) => {
//       const chefId = chefSnapshot.key;

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefSnapshot.child('wal');
//       const currentWalletValue = currentWalletSnapshot.val();

//       // Calculate the new wallet value by subtracting the daily cost
//       const newWalletValue = Math.max(0, currentWalletValue - dailyCost);

//       // Update the wallet value for the chef
//       await chefWalletRef.child(`${chefId}/wal`).set(newWalletValue);

//       // Update the stopped value based on the new wallet value
//       await chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//     });

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });










// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 18:20').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "0"
//     await chefOrderCounterRef.once('value', snapshot => {
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         chefOrderCounterRef.child(`${chefId}/cunt`).set("1");
//       });
//     });

//     // Get the daily cost value
//     const dailyCostSnapshot = await chefDailyCostRef.child('cost').once('value');
//     const dailyCost = dailyCostSnapshot.val();

//     // Get a snapshot of all chefIds in ChefWallet
//     const walletSnapshot = await chefWalletRef.once('value');

//     // Iterate through each chefId
//     walletSnapshot.forEach(async (chefSnapshot) => {
//       const chefId = chefSnapshot.key;

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefSnapshot.child('wal');
//       const currentWalletValue = currentWalletSnapshot.val();

//       // Calculate the new wallet value by subtracting the daily cost
//       const newWalletValue = currentWalletValue - dailyCost;

//       // Update the wallet value for the chef
//       await chefWalletRef.child(`${chefId}/wal`).set(newWalletValue);

//       // Update the stopped value based on the new wallet value
//       await chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//     });

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });



// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 04:00').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get the daily cost value
//     const dailyCostSnapshot = await chefDailyCostRef.child('cost').once('value');
//     const dailyCost = dailyCostSnapshot.val();

//     // Get a snapshot of all chefIds in ChefWallet
//     const walletSnapshot = await chefWalletRef.once('value');

//     // Iterate through each chefId
//     const promises = [];
//     walletSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefSnapshot.child('wal');
//       const currentWalletValue = currentWalletSnapshot.val();

//       // Calculate the new wallet value by subtracting the daily cost
//       const newWalletValue = currentWalletValue - dailyCost;

//       // Update the wallet value for the chef
//       promises.push(chefWalletRef.child(`${chefId}/wal`).set(newWalletValue));

//       // Update the stopped value based on the new wallet value
//       promises.push(chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0));
//     });

//     await Promise.all(promises);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });



// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 20:57').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];
//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;
      
//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);
//       costPromises.push(currentWalletSnapshot.once('value').then(walletSnapshot => {
//         const currentWalletValue = walletSnapshot.val();

//         // Calculate the new wallet value by subtracting the daily cost
//         const newWalletValue = currentWalletValue - dailyCost;

//         // Update the wallet value for the chef
//         return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue);

//       }));
      
//       // Update the stopped value based on the new wallet value
//       costPromises.push(chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0));
//     });

//     await Promise.all(costPromises);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });









// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 22:20').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];

//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;
      
//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

//       // Chain the promises to ensure proper execution order
//       costPromises.push(
//         currentWalletSnapshot.once('value')
//           .then(walletSnapshot => {
//             const currentWalletValue = walletSnapshot.val();

//             // Calculate the new wallet value by subtracting the daily cost
//             const newWalletValue = currentWalletValue - dailyCost;

//             // Update the wallet value for the chef
//             return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue);
//           })
//           .then(() => {
//             // Update the stopped value based on the new wallet value
//             return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//           })
//       );
//     });

//     // Wait for all promises to resolve
//     await Promise.all(costPromises);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });




//last final working..................................................................................................

// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 04:00').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');  

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];

//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;
      
//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

//       // Chain the promises to ensure proper execution order
//       costPromises.push(
//         currentWalletSnapshot.once('value')
//           .then(walletSnapshot => {
//             const currentWalletValue = walletSnapshot.val();

//             // Calculate the new wallet value by subtracting the daily cost
//             const newWalletValue = currentWalletValue - dailyCost;

//             // Update the wallet value for the chef
//             return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue)
//               .then(() => {
//                 // Update the stopped value based on the new wallet value
//                 return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//               });
//           })
//       );
//     });

//     // Wait for all promises to resolve
//     await Promise.all(costPromises);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });

//........................................................................................................................................




// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 03:13').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   // Reference to the AlreadyOrdered node
//   const alreadyOrderedRef = database.ref('/AlreadyOrdered');

//   // Reference to the CustomerPendingOrders node
//   const customerPendingOrdersRef = database.ref('/CustomerPendingOrders');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];

//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;

//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

//       // Chain the promises to ensure proper execution order
//       costPromises.push(
//         currentWalletSnapshot.once('value')
//           .then(walletSnapshot => {
//             const currentWalletValue = walletSnapshot.val();

//             // Calculate the new wallet value by subtracting the daily cost
//             const newWalletValue = currentWalletValue - dailyCost;

//             // Update the wallet value for the chef
//             return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue)
//               .then(() => {
//                 // Update the stopped value based on the new wallet value
//                 return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//               });
//           })
//       );
//     });

//     // Get a snapshot of all userids in CustomerPendingOrders
//     const pendingOrdersSnapshot = await customerPendingOrdersRef.once('value');

//     // Iterate through each userid in CustomerPendingOrders
//     const deletePromises = [];

//     pendingOrdersSnapshot.forEach(userSnapshot => {
//       const userId = userSnapshot.key;

//       // Check if the userid is not present in AlreadyOrdered or has isOrdered set to false
//       const alreadyOrderedPromise = alreadyOrderedRef.child(userId).once('value');
//       const isOrderedPromise = userSnapshot.child('isOrdered').val();

//       deletePromises.push(
//         Promise.all([alreadyOrderedPromise, isOrderedPromise])
//           .then(([alreadyOrderedSnapshot, isOrdered]) => {
//             if (!alreadyOrderedSnapshot.exists() || isOrdered === false) {
//               // Delete the userid from CustomerPendingOrders
//               return customerPendingOrdersRef.child(userId).remove();
//             }
//             return null;
//           })
//       );
//     });

//     // Wait for all promises to resolve
//     await Promise.all([...costPromises, ...deletePromises]);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });



// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 03:21').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   // Reference to the AlreadyOrdered node
//   const alreadyOrderedRef = database.ref('/AlreadyOrdered');

//   // Reference to the CustomerPendingOrders node
//   const customerPendingOrdersRef = database.ref('/CustomerPendingOrders');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];

//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;

//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

//       // Chain the promises to ensure proper execution order
//       costPromises.push(
//         currentWalletSnapshot.once('value')
//           .then(walletSnapshot => {
//             const currentWalletValue = walletSnapshot.val();

//             // Calculate the new wallet value by subtracting the daily cost
//             const newWalletValue = currentWalletValue - dailyCost;

//             // Update the wallet value for the chef
//             return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue)
//               .then(() => {
//                 // Update the stopped value based on the new wallet value
//                 return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//               });
//           })
//       );
//     });

//     // Get a snapshot of all userids in CustomerPendingOrders
//     const pendingOrdersSnapshot = await customerPendingOrdersRef.once('value');

//     // Iterate through each userid in CustomerPendingOrders
//     const deletePromises = [];

//     pendingOrdersSnapshot.forEach(userSnapshot => {
//       const userId = userSnapshot.key;

//       // Check if the userid is not present in AlreadyOrdered or has isOrdered set to false
//       deletePromises.push(
//         alreadyOrderedRef.child(userId).once('value')
//           .then(alreadyOrderedSnapshot => {
//             const isOrdered = userSnapshot.child('isOrdered').val();
//             if (!alreadyOrderedSnapshot.exists() || isOrdered === false) {
//               // Delete the userid from CustomerPendingOrders
//               return customerPendingOrdersRef.child(userId).remove();
//             }
//             return null;
//           })
//       );
//     });

//     // Wait for all promises to resolve
//     await Promise.all([...costPromises, ...deletePromises]);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });





// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 03:28').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   // Reference to the AlreadyOrdered node
//   const alreadyOrderedRef = database.ref('/AlreadyOrdered');

//   // Reference to the CustomerPendingOrders node
//   const customerPendingOrdersRef = database.ref('/CustomerPendingOrders');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];

//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;

//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

//       // Chain the promises to ensure proper execution order
//       costPromises.push(
//         currentWalletSnapshot.once('value')
//           .then(walletSnapshot => {
//             const currentWalletValue = walletSnapshot.val();

//             // Calculate the new wallet value by subtracting the daily cost
//             const newWalletValue = currentWalletValue - dailyCost;

//             // Update the wallet value for the chef
//             return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue)
//               .then(() => {
//                 // Update the stopped value based on the new wallet value
//                 return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//               });
//           })
//       );
//     });

//     // Get a snapshot of all userids in CustomerPendingOrders
//     const pendingOrdersSnapshot = await customerPendingOrdersRef.once('value');

//     // Iterate through each userid in CustomerPendingOrders
//     const deletePromises = [];

//     pendingOrdersSnapshot.forEach(userSnapshot => {
//       const userId = userSnapshot.key;

//       // Check if the userid is not present in AlreadyOrdered or has isOrdered set to false
//       deletePromises.push(
//         Promise.all([
//           alreadyOrderedRef.child(userId).once('value'),
//           Promise.resolve(userSnapshot.child('isOrdered').val())
//         ])
//           .then(([alreadyOrderedSnapshot, isOrdered]) => {
//             if (!alreadyOrderedSnapshot.exists() || isOrdered === false) {
//               // Delete the userid from CustomerPendingOrders
//               return customerPendingOrdersRef.child(userId).remove();
//             }
//             return null;
//           })
//       );
//     });

//     // Wait for all promises to resolve
//     await Promise.all([...costPromises, ...deletePromises]);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });



// const functions = require('firebase-functions');
// const admin = require('firebase-admin');
// admin.initializeApp();

// exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 03:36').timeZone('Asia/Kolkata').onRun(async (context) => {
//   const database = admin.database();

//   // Reference to the ChefOrderCounter node
//   const chefOrderCounterRef = database.ref('/ChefOrderCounter');

//   // Reference to the ChefDailyCost node
//   const chefDailyCostRef = database.ref('/ChefDailyCost');

//   // Reference to the ChefWallet node
//   const chefWalletRef = database.ref('/ChefWallet');

//   // Reference to the ChefStopped node
//   const chefStoppedRef = database.ref('/ChefStopped');

//   // Reference to the AlreadyOrdered node
//   const alreadyOrderedRef = database.ref('/AlreadyOrdered');

//   // Reference to the CustomerPendingOrders node
//   const customerPendingOrdersRef = database.ref('/CustomerPendingOrders');

//   try {
//     // Reset the cunt values in ChefOrderCounter to "1"
//     await chefOrderCounterRef.once('value', snapshot => {
//       const promises = [];
//       snapshot.forEach(chefSnapshot => {
//         const chefId = chefSnapshot.key;
//         promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
//       });
//       return Promise.all(promises);
//     });

//     // Get a snapshot of all chefIds in ChefDailyCost
//     const costSnapshot = await chefDailyCostRef.once('value');

//     // Iterate through each chefId in ChefDailyCost
//     const costPromises = [];

//     costSnapshot.forEach(chefSnapshot => {
//       const chefId = chefSnapshot.key;

//       // Get the daily cost value for the chef
//       const dailyCost = chefSnapshot.child('cost').val();

//       // Get the current wallet value for the chef
//       const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

//       // Chain the promises to ensure proper execution order
//       costPromises.push(
//         currentWalletSnapshot.once('value')
//           .then(walletSnapshot => {
//             const currentWalletValue = walletSnapshot.val();

//             // Calculate the new wallet value by subtracting the daily cost
//             const newWalletValue = currentWalletValue - dailyCost;

//             // Update the wallet value for the chef
//             return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue)
//               .then(() => {
//                 // Update the stopped value based on the new wallet value
//                 return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
//               });
//           })
//       );
//     });

//     // Get a snapshot of all userids in CustomerPendingOrders
//     const pendingOrdersSnapshot = await customerPendingOrdersRef.once('value');

//     // Iterate through each userid in CustomerPendingOrders
//     const deletePromises = [];

//     pendingOrdersSnapshot.forEach(userSnapshot => {
//       const userId = userSnapshot.key;

//       // Check if the userid is not present in AlreadyOrdered or has isOrdered set to "false"
//       deletePromises.push(
//         Promise.all([
//           alreadyOrderedRef.child(userId).once('value'),
//           Promise.resolve(userSnapshot.child('isOrdered').val() === 'false')
//         ])
//           .then(([alreadyOrderedSnapshot, isOrdered]) => {
//             if (!alreadyOrderedSnapshot.exists() || isOrdered) {
//               // Delete the userid from CustomerPendingOrders
//               return customerPendingOrdersRef.child(userId).remove();
//             }
//             return null;
//           })
//       );
//     });

//     // Wait for all promises to resolve
//     await Promise.all([...costPromises, ...deletePromises]);

//     return null;
//   } catch (error) {
//     console.error(error);
//     return null;
//   }
// });



const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.resetAllCountersAndDecrementWallet = functions.pubsub.schedule('every day 04:30').timeZone('Asia/Kolkata').onRun(async (context) => {
  const database = admin.database();

  // Reference to the ChefOrderCounter node
  const chefOrderCounterRef = database.ref('/ChefOrderCounter');

  // Reference to the ChefDailyCost node
  const chefDailyCostRef = database.ref('/ChefDailyCost');

  // Reference to the ChefWallet node
  const chefWalletRef = database.ref('/ChefWallet');

  // Reference to the ChefStopped node
  const chefStoppedRef = database.ref('/ChefStopped');

  // Reference to the AlreadyOrdered node
  const alreadyOrderedRef = database.ref('/AlreadyOrdered');

  // Reference to the CustomerPendingOrders node
  const customerPendingOrdersRef = database.ref('/CustomerPendingOrders');

  try {
    // Reset the cunt values in ChefOrderCounter to "1"
    await chefOrderCounterRef.once('value', snapshot => {
      const promises = [];
      snapshot.forEach(chefSnapshot => {
        const chefId = chefSnapshot.key;
        promises.push(chefOrderCounterRef.child(`${chefId}/cunt`).set("1"));
      });
      return Promise.all(promises);
    });

    // Get a snapshot of all chefIds in ChefDailyCost
    const costSnapshot = await chefDailyCostRef.once('value');

    // Iterate through each chefId in ChefDailyCost
    const costPromises = [];

    costSnapshot.forEach(chefSnapshot => {
      const chefId = chefSnapshot.key;

      // Get the daily cost value for the chef
      const dailyCost = chefSnapshot.child('cost').val();

      // Get the current wallet value for the chef
      const currentWalletSnapshot = chefWalletRef.child(`${chefId}/wal`);

      // Chain the promises to ensure proper execution order
      costPromises.push(
        currentWalletSnapshot.once('value')
          .then(walletSnapshot => {
            const currentWalletValue = walletSnapshot.val();

            // Calculate the new wallet value by subtracting the daily cost
            const newWalletValue = currentWalletValue - dailyCost;

            // Update the wallet value for the chef
            return chefWalletRef.child(`${chefId}/wal`).set(newWalletValue)
              .then(() => {
                // Update the stopped value based on the new wallet value
                return chefStoppedRef.child(`${chefId}/stopped`).set(newWalletValue <= 0 ? 1 : 0);
              });
          })
      );
    });

    // Get a snapshot of all userids in CustomerPendingOrders
    const pendingOrdersSnapshot = await customerPendingOrdersRef.once('value');

    // Iterate through each userid in CustomerPendingOrders
    const deletePromises = [];

    pendingOrdersSnapshot.forEach(userSnapshot => {
      const userId = userSnapshot.key;

      // Check if the userid is not present in AlreadyOrdered or has isOrdered set to "false"
      deletePromises.push(
        alreadyOrderedRef.child(userId).once('value')
          .then(alreadyOrderedSnapshot => {
            const isOrdered = userSnapshot.child('isOrdered').val();
            if (!alreadyOrderedSnapshot.exists() || isOrdered === "false") {
              // Delete the userid from CustomerPendingOrders
              return customerPendingOrdersRef.child(userId).remove();
            }
            return null;
          })
      );
    });

    // Wait for all promises to resolve
    await Promise.all([...costPromises, ...deletePromises]);

    return null;
  } catch (error) {
    console.error(error);
    return null;
  }
});



















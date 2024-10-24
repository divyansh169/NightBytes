// const functions = require("firebase-functions");
// const Razorpay = require("razorpay"); 


// exports.createRazorpayOrder = functions.https.onRequest((request, response) => {
//     var instance = new Razorpay({
//         // key_id: 'rzp_live_cZZPCw7KRIyrHE',
//         // key_secret: 'vIQ1yUknoXYAR5XnVOA9dEdX',
//         key_id: request.query.kid,
//         key_secret: request.query.ksec,
//       });
//       var options = {
//         amount: request.query.amt,  // amount in the smallest currency unit
//         // amount: 215*100,  // amount in the smallest currency unit
//         currency: "INR",
//         receipt: "rcpt"
//       };
//       instance.orders.create(options, function(err, order) {
//         if(err != null){
//             response.json(
//               {error: err}
//             )
//         }
//         else {
//             response.json(
//               {orderID: order.id}
//             )
//         }
//       });
// });



const functions = require("firebase-functions");
const Razorpay = require("razorpay");
const cors = require('cors')({origin: true});

exports.createRazorpayOrder = functions.https.onRequest((request, response) => {
    cors(request, response, () => {
        const { kid, ksec, amt } = request.query;
        
        if (!kid || !ksec || !amt) {
            return response.status(400).json({ error: 'Missing required parameters' });
        }

        var instance = new Razorpay({
            key_id: kid,
            key_secret: ksec,
        });

        var options = {
            amount: amt,  // amount in the smallest currency unit
            currency: "INR",
            receipt: "rcpt"
        };

        instance.orders.create(options, function(err, order) {
            if (err) {
                response.status(500).json({ error: err });
            } else {
                response.status(200).json({ orderID: order.id });
            }
        });
    });
});


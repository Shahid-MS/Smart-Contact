// console.log("Started");
// alert("project started");

const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "18%");
  }
};

const deleteContact = (cId) => {
  // console.log("cId");
  swal({
    title: "Are you sure?",
    text: "Once deleted, you will not be able to recover this Contact!",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then((willDelete) => {
    if (willDelete) {
      window.location = "/user/delete/" + cId;
    } else {
      swal("Your Contact is Safe");
    }
  });
};

const search = () => {
  // console.log("Searching");
  let query = $("#search-input").val();
  if (query === "") {
    $(".search-result").hide();
  } else {
    // console.log(query);
    let url = `http://localhost:8080/search/${query}`;
    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        // console.log(data);
        let text = `<div class='search list-group'>`;
        data.forEach((contact) => {
          text += `<a href='/user/contact/${contact.id}' class='list-group-item list-group-item-action'> ${contact.name}</a>`;
        });

        text += `</div>`;

        $(".search-result").html(text);
        $(".search-result").show();
      });
  }
};

// payment

//first request to server to create order
const paymentStart = () => {
  // alert("Payment started");
  let amount = $("#payment_field").val();
  // console.log(amount);
  if (amount == "" || amount == null) {
    // alert("Please fill the amount");
    swal("Amount", "Amount is required", "error");
    return;
  }

  //use ajax to send request to server to create order

  $.ajax({
    url: "/user/create_order",
    data: JSON.stringify({ amount: amount }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      // invoked if success
      // console.log(response);
      if (response.status == "created") {
        // open payment form
        let options = {
          key: "******Key_id",
          amount: response.amount,
          currency: "INR",
          description: "Donation",
          image:
            "https://avatars.githubusercontent.com/u/112958600?s=400&u=c66802efda5009688aa79feac96b78dd31fd28df&v=4",
          name: "Smart Contact",
          order_id: response.id,

          handler: function (response) {
            // console.log(response.razorpay_payment_id);
            // console.log(response.razorpay_order_id);
            // console.log(response.razorpay_signature);
            // console.log("Payment successful");
            // alert("Payment success");
            updatePaymentOnServer(
              response.razorpay_payment_id,
              response.razorpay_order_id,
              "paid"
            );
            // swal("Thanks!", "Payment successful !", "success");
          },

          prefill: {
            name: "",
            email: "",
            contact: "",
          },
          notes: {
            address: "Smart Contact",
          },
          theme: {
            color: "#3399cc",
          },
        };

        let rzp = new Razorpay(options);
        rzp.on("payment.failed", function (response) {
          // console.log(response.error.code);
          // console.log(response.error.description);
          // console.log(response.error.source);
          // console.log(response.error.step);
          // console.log(response.error.reason);
          // console.log(response.error.metadata.order_id);
          // console.log(response.error.metadata.payment_id);
          // alert("Payment failed");
          swal("Oops!", "Payment unsuccessful", "error");
        });

        rzp.open();
      }
    },

    error: function (error) {
      // invoked when error
      // console.log(error);
    },
  });
};

const updatePaymentOnServer = (payment_id, order_id, status) => {
  $.ajax({
    url: "/user/update_order",
    data: JSON.stringify({
      payment_id: payment_id,
      order_id: order_id,
      status: status,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      // console.log("Success");
      swal("Thanks!", "Payment successful !", "success");
    },

    error: function (error) {
      // console.log("error");
      swal("Oops!", "Payment successful, but we dont get on server, Refund will be initiated within 5 working days", "error");
    },
  });
};

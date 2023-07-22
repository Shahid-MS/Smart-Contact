console.log("Started");
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

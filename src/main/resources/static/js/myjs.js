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

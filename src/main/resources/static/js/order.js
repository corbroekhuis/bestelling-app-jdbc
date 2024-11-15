var api = "/api/order" ;
var orderTable;

function init(){

    console.log('inside init' );

    $("#new-order-button").click( function () {
        console.log("Inside click of new-order-button");
        getArticleData();
        $('#order-modal').modal('show');
    });

    $("#edit-order-button").click( function () {
        console.log("Inside click of editOrderButton");
        // Get the data from selected row and fill fields in modal

        if (orderTable.row($('.selected')).data() == undefined) {
            alert("Select order first");
        }else{
            var order = orderTable.row($('.selected')).data();
            getArticleData();

            $("#id").val(order.id);
            $("#articleId").val(order.articleId);
   //         $('#dropdown option[value=' + getArticleData() + ']').attr('selected','selected');
            $("#dropdown").val(order.articleId).change();
            $("#quantity").val(order.quantity);
            $("#date").val(order.date);
            $('#order-modal').modal('show');
        }
    });

    $("#delete-order-button").click( function () {
        console.log("Inside click of deleteOrderButton");

        if (orderTable.row($('.selected')).data() == undefined) {
            alert("Select order first");
        }else{
            $('#order-delete-modal').modal('show');
        }

    });

    // Button in modal
    $("#delete-order-confirm-button").click( function () {
        console.log("Inside click of deleteOrderButton");
        deleteOrder();
        $('#order-delete-modal').modal('hide');
    });

    // Add submit event to form for new and edit
    $("#order-form").on('submit', function() {
        console.log("Submitting");
        createOrder();
        $('#order-modal').modal('hide');
    });

    initOrderTable();
    // Get orders from backend and and update table
    getOrderData();

}

function initOrderTable() {

    console.log('inside initOrderTable' );

    // Create columns (with titles) for datatable: id, name, address, age

    columns = [

        { "title":  "Order ID",
            "data": "id" ,
            "visible": false },
        { "title":  "Article ID",
            "data": "articleId" ,
            "visible": false },
        { "title":  "Artikel",
            "data": "articleId",
            "render": function(articleId) {
               return getArticleName( articleId);
            }},
        { "title":  "Aantal",
            "data": "quantity" },
        { "title": "Datum",
            "data": "date"}
    ];

    // Define new table with above columns
    orderTable = $("#orderTable").DataTable( {
        "order": [[ 1, "asc" ]],
        "columns": columns
    });


    $("#orderTable tbody").on( 'click', 'tr', function () {
        console.log("Clicking on row");
        if ( $(this).hasClass('selected') ) {
          $(this).removeClass('selected');
          // emptyRoomModals();
        }
        else {
            orderTable.$('tr.selected').removeClass('selected');
          // emptyRoomModals();
            $(this).addClass('selected');
        }
    });

}

function getOrderData(){

    console.log('inside getOrderData' );
    // http:/localhost:9090/api/order
    // json list of orders
    $.ajax({
        url: api,
        type: "get",
//        async: false,
        dataType: "json",
        // success: function(orders, textStatus, jqXHR){
        success: function(orders){

 //           console.log('Data: ' + orders );

            if (orders) {
                orderTable.clear();
                orderTable.rows.add(orders);
                orderTable.columns.adjust().draw();
            }
        },

        fail: function (error) {
            console.log('Error: ' + error);
        }

    });

}

function createOrder(){

    var method = "post";

    console.log('inside createOrder' + $("#id").val());

    // aaaPut order data from page in Javascript object --- SIMILAR TO JSON
    var orderData = $("#id").val() == 0 ? {
//        articleId: $("#articleId").val(),
        articleId: $('#dropdown').find(":selected").val(),
        quantity: $("#quantity").val(),
        date: $("#date").val()

    }:{
        id: $("#id").val() ,
//        articleId: $("#articleId").val(),
        articleId: $('#dropdown').find(":selected").val(),
        quantity: $("#quantity").val(),
        date: $("#date").val()
  };

    // Transform Javascript object to json
    var orderJson = JSON.stringify(orderData);

    console.log(orderJson);

    $.ajax({
        url: api,
        async: false,
        type: method,
        data: orderJson,    // json for request body
        contentType:"application/json; charset=utf-8",   // What we send to frontend
        dataType: "json",  // get back from frontend
        // success: function(order, textStatus, jqXHR){
        success: function(order){

          console.log(order);

          // Clear fields in page

          $("#id").val('');
          $("#articleId").val('');
          $("#dropdown").empty();
          $("#quantity").val('');
          $("#date").val('');

          // Refresh table data
          getOrderData();

        },

        fail: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.status);
                    alert(textStatus);
                    alert(errorThrown);

                    console.log('Fail: ' + jqXHR.status);
        },
        error: function(error) {
            alert(error);

            console.log('Error: ' + error);

        }

    });

}

function deleteOrder(){

    if (orderTable.row($('.selected')).data() == undefined) {
        alert("Select order first");
    }else{
        var order = orderTable.row($('.selected')).data();

        console.log(api + '/' + order.id);

            $.ajax({
                url: api + '/' + order.id,
                type: "delete",
//                async: false,
                dataType: "text",  // get back from frontend
                // success: function(order, textStatus, jqXHR){
                success: function(message){

                  console.log(message);

                  // Refresh table data
                  getOrderData();

                },

                fail: function (jqXHR, textStatus, errorThrown) {
                            alert(jqXHR.status);
                            alert(textStatus);
                            alert(errorThrown);

                            console.log('Fail: ' + jqXHR.status);
                },
                error: function(error) {
                    alert(error);

                    console.log('Error: ' + error);

                }

            });
    }
}

function getArticleData(){

    console.log('inside getArticleData' );
    // http:/localhost:9090/api/order
    // json list of orders
    $.ajax({
        url: "/api/article",
        type: "get",
        async: false,
        dataType: "json",
        // success: function(orders, textStatus, jqXHR){
        success: function(articles){

            console.log('Data: ' + articles );

            helpers.buildDropdown(
   //                         jQuery.parseJSON(articles),
                articles,
                $('#dropdown'),
                'Kies een artikel'
            );
        },

        fail: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.status);
                    alert(textStatus);
                    alert(errorThrown);

                    console.log('Fail: ' + jqXHR.status);
        },
        error: function(error) {
            alert(error);

            console.log('Error: ' + error);

        }

    });

}

var helpers =
{
    buildDropdown: function(result, dropdown, emptyMessage)
    {
        // Remove current options
        dropdown.html('');

        // Add the empty option with the empty message
        dropdown.append('<option value="">' + emptyMessage + '</option>');

        // Check result isnt empty
        if(result != '')
        {
            // Loop through each of the results and append the option to the dropdown
            $.each(result, function(k, v) {
                dropdown.append('<option value="' + v.id + '">' + v.name + '</option>');
            });
        }
    }
}

function getArticleName( id){

//    return "Slim Bike ZW";
    var articleName = "Not found";

    $.ajax({
        url: "api/article/"+ id,
        type: "get",
        async: false,
        dataType: "json",
        // success: function(orders, textStatus, jqXHR){
        success: function(article){

 //           console.log('Data: ' + orders );

            if (article) {
                articleName =  article.name;
            }
        },

        fail: function (error) {
            console.log('Error: ' + error);
        }

    });

    return articleName;

}
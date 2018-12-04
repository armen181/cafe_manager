$(document).ready(function () {
    //===========  open  product create model ====================
    $('body').on("click", "#createProductBtn", function (e) {
        $('#createProduct').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  product create model ====================
    $('body').on("click", "#createProductButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productCreate",
            "method": "GET",
            "headers": {
                "Name": $('#nameForCreateProduct').val()
            }
        };
        $.ajax(settings).done(function (response) {
            update();
            $('#createProduct').modal('hide');

        });


    });

    $('body').on("click", ".productList", function (e) {
       update();

    });

//===========  open  product edit model ====================
    $('body').on("click", ".productEdit", function (e) {
        $('#productNameForChange').text(e.currentTarget.value);
        $('#editProduct').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  project edit model and save ====================
    $('body').on("click", "#editEditButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productEdit",
            "method": "GET",
            "headers": {
                "id": $('#productNameForChange').text(),
                "productName": $('#nameForProductEdit').val()
            }
        };
        $.ajax(settings).done(function (response) {
            update();
            $('#editProduct').modal('hide');

        });


    });

    //===========  open  project delete model ====================
    $('body').on("click", ".deleteProduct", function (e) {
        $('#productIdForRemove').text(e.currentTarget.value);
        $('#removeProduct').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  project remove model ====================
    $('body').on("click", "#removeProductButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productRemove",
            "method": "GET",
            "headers": {
                "id": $('#productIdForRemove').text()
            }
        };
        $.ajax(settings).done(function (response) {
            update();
            $('#removeProduct').modal('hide');

        });


    });
});

function updateProductList(response) {
    var html = "<div id=\"table\">\n";
    response.forEach(function (x) {
       html +="<nav class=\"navbar bg-white navbar-light\">\n" +
           "            <div class=\"row\">\n" +
           "                <button class=\"navbar-toggler btn-info\" type=\"button\" >\n" +
           "                    "+x.id+"\n" +
           "                </button>\n" +
           "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
           "                   "+ x.name +"\n" +
           "                </button>\n" +
           "            </div>\n" +
           "            <div class=\"row\">\n" +
           "                <button class=\"navbar-toggler btn-secondary productEdit\" type=\"button\" value=\""+x.id+"\" >\n" +
           "                    Edit\n" +
           "                </button>\n" +
           "                <button class=\"navbar-toggler btn-danger deleteProduct\" type=\"button\" value=\""+x.id+"\">\n" +
           "                    Delete\n" +
           "                </button></div></nav>"

    })
    $('#table').replaceWith( html+"</div>" );
}
function update() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/rest/productGetAll",
        "method": "GET",
        "headers": {
        }
    }

    $.ajax(settings).done(function (response) {
        updateProductList(response);
    });
}
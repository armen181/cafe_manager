$(document).ready(function () {
//===========  open  User create model ====================
    $('body').on("click", "#createUserBtn", function (e) {

        $('#eMailForCreateUser').val("");
        $('#firstNameForCreateUser').val("");
        $('#lastNameForCreateUser').val("");
        $('#passwordForCreateUser').val("");
        $('#roleLabelForCreateUser').val("WAITER");
        $('#createUser').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');
    });
    //===========  User create model ====================
    $('body').on("click", "#createUserButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/userCreat",
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "processData": false,
            "data": "{\n    " +
                "\"firsName\": \""+$('#firstNameForCreateUser').val()+"\",\n   " +
                " \"lastName\": \""+$('#lastNameForCreateUser').val()+"\",\n   " +
                " \"role\": \""+$('#roleLabelForCreateUser').val()+"\",\n   " +
                " \"email\": \""+$('#eMailForCreateUser').val()+"\",\n    " +
                "\"userPassword\": \""+$('#passwordForCreateUser').val()+"\"\n}"
        };

        $.ajax(settings).done(function (response) {
            $('#createUser').modal('hide');
        });

    });

    $('body').on("click", ".userList", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/userGetAll",
            "method": "GET",
            "headers": {
            }
        }

        $.ajax(settings).done(function (response) {
            updateUserList(response);
        });


    });

    //===========  User remove model ====================
    $('body').on("click", "#removeUserButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/userRemoveById",
            "method": "GET",
            "headers": {
                "id": $('#userNameForRemove').text()
            }
        };
        $.ajax(settings).done(function (response) {
            $('#removeUser').modal('hide');

        });


    });

    //===========  open  project delete model ====================
    $('body').on("click", ".deleteUser", function (e) {
        $('#userNameForRemove').text(e.currentTarget.value);
        $('#removeUser').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

});


function updateUserList(response) {
    var html = "<div id=\"table\">\n";
    response.forEach(function (x) {
        html +="<nav class=\"navbar bg-white navbar-light\">\n" +
            "            <div class=\"row\">\n" +
            "                <button class=\"navbar-toggler btn-info\" type=\"button\" >\n" +
            "                    "+x.id+"\n" +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                   "+ x.email +"\n" +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                   "+ x.firsName +"\n" +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                   "+ x.lastName +"\n" +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                   "+ x.role +"\n" +
            "                </button>\n" +
            "            </div>\n" +
            "            <div class=\"row\">\n" +
            "                <button class=\"navbar-toggler btn-danger deleteUser\" type=\"button\" value=\""+x.id+"\">\n" +
            "                    Delete\n" +
            "                </button></div></nav>"

    })
    $('#table').replaceWith( html+"</div>" );
}

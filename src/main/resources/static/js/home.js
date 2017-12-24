
// import * as $ from "./lib/jquery";

/**
 * Created by macbookair on 08.03.17.
//  */

$.validator.classRuleSettings.className = {emailPattern: true};

$.validator.classRuleSettings.className = {passwordPattern: true};
// });

var recordIdGlobal;
(function () {


    getSession();

    $('[data-toggle="tooltip"]').tooltip();

    // $('#fullscreen').on('click', function(event) {
    //     event.preventDefault();
    //     window.parent.location = "http://bootsnipp.com/iframe/4l0k2";
    // });
    $('a[href="#cant-do-all-the-work-for-you"]').on('click', function (event) {
        event.preventDefault();
        $('#cant-do-all-the-work-for-you').modal('show');
    });

    // $('a[href="#changeBlockModal"]').on('click', function (event) {
    //     event.preventDefault();
    //     $('#changeBlockModal').modal('show');
    // });

    // $('[data-command="toggle-search"]').on('click', function (event) {
    //     event.preventDefault();
    //     $(this).toggleClass('hide-search');
    //
    //     if ($(this).hasClass('hide-search')) {
    //         $('.c-search').closest('.row').slideUp(100);
    //     } else {
    //         $('.c-search').closest('.row').slideDown(100);
    //     }
    // });


    function getSession() {
        // var id = {accountId: accountId};
        $.ajax({
            url: '/records/' + accountId,
            method: 'GET',
            // contentType: "application/json; charset=utf-8",
            // data: id,
            success: function (response, status) {
                // session.arrMap = [];
                // Object.values(session.map).forEach(value => session.arrMap.push(value));

                drawInfo(response);

            },
            error: function (status) {
                console.log(status);
            }
        });
    }

    $('#navbar-brand-click').on('click', function () {
        $("#search1").val("");
        $('#search2').val("");
        $('#search3').val("");
        getSession();

    });

    //Доделать


    function drawInfo(record) {

        $("#contact-list").empty();

        for (var i = 0, j = record.length; i < j; i++) {

            $("#contact-list").append(getBlock(record[i])
                );
        }


    }



    let flugFormConfirm = true;

    $("email-input").rules("add", {
        required: false,
        minLength: 3,
        pattern: /(([\w\.-]*[a-zA-Z0-9_]){3,}@([\w\.-]*[a-zA-Z0-9]){4,}\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z])*/,
        messages: {
            required: "Insert your name.",
            minLength: "min 3 chars below @...",
        }


    });





    $('#filterButton').on('click', function () {

        let form = $('#sortForm');
        let nameS = form.find('#search1').val();
        let surnameS = form.find('#search2').val();
        let mobileNumberS = validNumberSearch(form.find('#search3').val(), 6);

        //
        let href = "/recordSorted/" + accountId + "";


        if (nameS == "" && surnameS == "" && mobileNumberS == "") {
            getSession();
            console.log("Для поиска данные не введены");
            $('#sidebar').append("Введите данные для фильтрации")
        } else {
            // let filter = form.serialize();
            filter = {name: nameS, surname: surnameS, mobileNumber: mobileNumberS};
            $.ajax({

                url: href,
                method: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(filter),
                dataType: 'json',
                success: function (response, status) {
                    // session.arrMap = [];
                    // Object.values(session.map).forEach(value => session.arrMap.push(value));

                    drawInfo(response);

                },
                error: function (status) {
                    console.log(status);
                }
            });
        }


    });


    function validNumberSearch(number, length) {

        if (number != "") {
            if (number.length < length) {
                return null;
            } else {
                return number
            }
        } else return null;


    }

    function validTextSearch(name) {
        if (name == "") {
            return null;
        } else {
            return name;
        }
    }


    function drawChangeOneRecord(record){


        $("#contact-list").find("#"+record.id).val(getBlock(record));

    }



    function drawOneRecord(record) {

        $("#contact-list").prepend(getBlock(record));
    }

    function getBlock(record) {

        let email = record.email;
        let homeNumber = record.homeNumber;
        let address = record.address;

        if (email == null) {
            email = ""
        }
        if (homeNumber == null) {
            homeNumber = "";
        }
        if (address == null) {
            address = "";
        }

        var block = '<li class="list-group-item" id="' + record.id + '">' +'<div class="row-fluid">'+
            '<div class="row">' +
            '<div class="col-xs-12 col-sm-4 first">' +
            '<img src="http://waaves.hu/img/contact.png" alt="Contact" class="img-responsive img-circle"/>' +
            '</div>' +
            '<div class="col-xs-12 col-sm-8  second">' +
            '<span class="name">' + record.name + '</span><br/>' +
            '<span class="surname">' + record.surname + '</span><br/>' +
            '<span class="lastname">' + record.lastname + '</span><br/>' +
            '<p class="inline"><b class="text-info">Mobile number: </b><h5 class="mobileNumber">' + record.mobileNumber + '</h5></p>' +
            '<p class="inline"><b class="text-info">Home number: </b><h5 class="homeNumber">' + homeNumber + '</h5></p>' +
            '<p class="inline"><b class="text-info">Email:</b><h5 class="email1">' + email + '</h5></p>' +
            '<p class="inline"><b class="text-info">Address:</b><h5 class="address">' + address + '</h5></p>' +
            '</div>' +
            '</div>' +
            '<div class="row">' +
            '<div class="btn-group" role="group" aria-label="Basic example">' +
            '<a type="button" class="btn btn-lg btn-primary btn-group" href="#changeBlockModal" onclick="changeButtonLoadData(' + record.id+')" data-toggle="tooltip" data-placement="top">Change</a>' +
            '<button type="button" class="btn btn-lg btn-warning btn-group" onclick="deleteButton(' + record.id + ')">' +
            '<span class="glyphicon glyphicon-trash"></span></button>' +
            '</div>' +
            '</div>' +
            '</div>' +

            '</li>';

        return block;
    }


    $("#email-input").on('change keyup paste', function () {
        var email = $(this).val();
        if (email !== "") {

            if (validateEmail(email)) {
                var idx1 = email.lastIndexOf("@");
                if (idx1 > 2) {
                    flugFormConfirm = true;
                } else {
                    flugFormConfirm = false;
                }
            } else {
                // alert("You have entered an invalid email address!");
                flugFormConfirm = false;
            }

        } else {
            flugFormConfirm = true;
        }

    });




    $("#idAddForm").submit(function() {
    /* Do Something */
        let form = $('#idAddForm');
        var val = form.validate();

         if( val != "novalidate"){
             // console.log(val+" _____");
             let homeNumber1;
             if ($("#homeNumber-input").val() =="+38044 "){
                 homeNumber1 = "";
             }else {
                 homeNumber1 = $('#homeNumber-input').val();
             }
             let clientOrder = {
                         name: $("#name-input").val(),
                         surname: $("#surname-input").val(),
                         lastName: $("#lastName-input").val(),
                         mobileNumber: $("#mobileNumber-input").val(),
                         homeNumber: homeNumber1,
                         address: $("#address-input").val(),
                         email: $('#email-input').val()
                     };
             $.ajax({
                 url: "/recordAdd/" + accountId,
                 contentType: "application/json; charset=utf-8",
                 method: "POST",
                 type: "JSON",
                 data: JSON.stringify(clientOrder),
                 success: function (record, status) {
                     drawOneRecord(record);
                     // console.log(status);
                     // alert(data);
                 },
                 error(status){
                     // console.log(status);
                 }

             });

         }else {console.log("No validate")}


    });

    $('#idChangeForm').submit(function () {

        let form = $('#idChangeForm');
        var val = form.validate();

        let homeNumber1;
        if ($("#homeNumber-change").val() =="+38044 "){
            homeNumber1 = "";
        }else {
            homeNumber1 = $('#homeNumber-change').val();
        }
        let clientOrder = {
            name: $("#name-change").val(),
            surname: $("#surname-change").val(),
            lastName: $("#lastName-change").val(),
            mobileNumber: $("#mobileNumber-change").val(),
            homeNumber: homeNumber1,
            address: $("#address-change").val(),
            email: $('#email-change').val()
        };
        $.ajax({
            url: "/recordChange/" + accountId +"/"+recordIdGlobal ,
            contentType: "application/json; charset=utf-8",
            method: "POST",
            type: "JSON",
            data: JSON.stringify(clientOrder),
            success: function (record, status) {
                drawChangeOneRecord(record);
                console.log(status);
                // alert(data);
            },
            error(status){
                console.log(status);
            }

        });

    })





})();
function validateEmail(email) {
    var re = /(([\w\.-]*[a-zA-Z0-9_]){3,}@([\w\.-]*[a-zA-Z0-9]){4,}\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z])*/;
    return re.test(email);
}
function deleteButton(recordId) {
    $.ajax({
        url: '/deleteRecord/' + recordId,
        method: 'DELETE',
        success: function (status) {
            // session.arrMap = [];
            // Object.values(session.map).forEach(value => session.arrMap.push(value));
            console.log(status);
            $('#' + recordId).remove();

        },
        error: function (error) {
            console.log(error);
        }
    });

}
function loadData(recordId){

    recordIdGlobal = recordId;

    $.ajax({

        url: "/recordOne/"+recordId,
        method: "GET",
        success: function (record, status) {
            $("#name-change").val(record.name);
            $("#surname-change").val(record.surname);
            $("#lastName-change").val(record.lastname);
            $("#mobileNumber-change").val(record.mobileNumber);
            if (record.homeNumber != ""){
                $("#homeNumber-change").val(record.homeNumber);
            }
            if (record.email != ""){
                $("#email-change").val(record.email);
            }
            if (record.address != ""){
                $("#address-change").val(record.address);
            }
        },
        error: function (error) {
            console.log(error);
        }

    });

}
function changeButtonLoadData(recordId) {

    $('#changeBlockModal').modal('show');
    event.preventDefault();
    recordIdGlobal = recordId;
    // console.log(name);
    loadData(recordId);

}

// $('#addRecordButton').on('click', function () {
//
//     let form = $('#idAddForm');
//     let name1 = form.find('#name-input').val();
//     let surname1 = form.find('#surname-input').val();
//     let lastname1 = form.find('#lastName-input').val();
//     let mobile = form.find('#mobileNumber-input').val();
//
//     if (name1.length() < 5 || surname1.length() < 5 || lastname1 < 5) {
//         flugFormConfirm = false;
//         alert("Name, Surname, Last name must not be less then 5 chars");
//     }else if (flugFormConfirm) {
//
//         let clientOrder = {
//             accountId: accountId,
//             recordId: '',
//             name: name1,
//             surname: surname1,
//             lastName: lastname1,
//             mobileNumber: mobile,
//             homeNumber: validNumberSearch(form.find('#homeNumber-input').val(), 16),
//             address: validTextSearch(form.find('#address-input').val()),
//             email: form.find('#email-input').val()
//         };
//
//         console.log(clientOrder);
//         $.ajax({
//             url: '/recordAdd',
//             method: 'POST',
//             contentType: "application/json; charset=utf-8",
//             data: JSON.stringify(clientOrder),
//             success: function (record, status) {
//                 // session.arrMap = [];
//                 // Object.values(session.map).forEach(value => session.arrMap.push(value));
//
//                 drawOneRecord(record);
//                 console.log(status);
//             },
//             error: function (status) {
//                 console.log(status);
//             }
//         });
//     }else {
//         alert("Email is invailid");
//     }
//
// });


// $('#idAddForm').validate({ // initialize plugin
//     // your rules & options,
//     rules: {
//         name: {
//             required: true,
//             minLength: 4
//         },
//         surname: {
//             required: true,
//             minLength: 4
//         },
//         lastName: {
//             required: true,
//             minLength: 4
//         },
//         mobileNumber: {
//             required: true,
//             minLength: 19
//         },
//         homeNumber: {
//             required: false,
//             minLength: 16
//         },
//         email: {
//             required: false,
//             email: true,
//             emailPattern: true
//         },
//         address: {
//             required: false,
//             minLength: 4
//         }
//     },
//     messages: {
//         name: {
//             required: "Please enter it is required",
//             minLength: "Name min 4 chars"
//         },
//         surname: {
//             required: "Please enter it is required",
//             minLingth: "Surname min 4 chars",
//         },
//         lastName: {
//             required: "Please enter it is required",
//             minLength: "Last name min 4 chars",
//         },
//         mobileNumber: {
//             required: "Please enter mobile number",
//             minLength: "it is not valid number +380 (dd) ddd-dd-dd"
//         },
//         homeNumber: {
//             required: "Please enter home ",
//         },
//         email: {
//             email: "Email min 4 chars before '@'",
//             emailPattern : "It is not valid email",
//         },
//         address: {
//             minLength: "Please enter address",
//         }
//     },
//     submitHandler: function (form) {
//         var flag = true;
//         if (form.email != "") {
//             flag = !!validateEmail(form.email);
//         }else {flag = true;}
//         if (form.mobileNumber != ""){
//             flag = !! validNumberSearch(form.mobileNumber, 19);
//         }else {flag = true;}
//         if (form.homeNumber != ""){
//             flag= !! validNumberSearch(form.homeNumber, 16);
//         }else {flag = true;}
//         if (form.address != ""){
//             flag = !!validTextSearch(form.address);
//         }else {flag = true;}
//         // if (f)
//         if (flag) {
//             $(form).ajaxSubmit({
//                 url: "/recordAdd/" + accountId,
//                 contentType: "application/json; charset=utf-8",
//                 type: "POST",
//                 success: function (record, status) {
//                     drawOneRecord(record);
//                     console.log(status);
//                     // alert(data);
//                 },
//                 error(status){
//                     console.log(status);
//                 }
//             });
//         }
//         // your ajax would go here
//         // let name1 = form.find('#name-input').val();
//         // let surname1 = form.find('#surname-input').val();
//         // let lastname1 = form.find('#lastName-input').val();
//         // let mobile = form.find('#mobileNumber-input').val();
//
//         // if (name1.length < 5 || surname1.length < 5 || lastname1 < 5) {
//         //     flugFormConfirm = false;
//         //     alert("Name, Surname, Last name must not be less then 5 chars");
//         // }else if (flugFormConfirm) {
//
//
//         // let clientOrder = {
//         //     accountId: accountId,
//         //     name: name1,
//         //     surname: surname1,
//         //     lastName: lastname1,
//         //     mobileNumber: mobile,
//         //     homeNumber: validNumberSearch(form.find('#homeNumber-input').val(), 16),
//         //     address: validTextSearch(form.find('#address-input').val()),
//         //     email: form.find('#email-input').val()
//         // };
//         // var formJ = $('#idAddForm').serializeJSON();
//         // var obj = {fields: };
//
//         // console.log(clientOrder);
//
//         // $.ajax({
//         //     url: '/recordAdd',
//         //     method: 'POST',
//         //     contentType: "application/json; charset=utf-8",
//         //     data: JSON.stringify(form),
//         //     success: function (record, status) {
//         //         // session.arrMap = [];
//         //         // Object.values(session.map).forEach(value => session.arrMap.push(value));
//         //
//         //         drawOneRecord(record);
//         //         console.log(status);
//         //     },
//         //     error: function (status) {
//         //         console.log(status);
//         //     }
//         // });
//         //
//         // form.submit();
//         // }else {
//         //     alert("Email is invailid");
//         // }
//         // return false;
//     }
// });

// function addRecord() {

// }

// $('#contact-list').searchable({
//     searchField: '#contact-list-search',
//     selector: 'li',
//     childSelector: '.col-xs-12',
//     show: function( elem ) {
//         elem.slideDown(100);
//     },
//     hide: function( elem ) {
//         elem.slideUp( 100 );
//     }
// })
//
// function byNameFunc() {
//
// }


// $("#sortForm").submit(function() {
//     /* Do Something */
//
//
// });















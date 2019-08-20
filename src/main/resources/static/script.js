function alertError() {
    alert("Not Saved");
}

function getParsedPhoneNumber() {
    var indicative = $("#indicativeSelection").val();
    var phoneNumber = $("#numberInput").val();

    var fullPhoneNumber = "(" + indicative + ") " + phoneNumber;
    return fullPhoneNumber;
}

function submitCustomer() {
    var customerName = $("#nameInput").val();
    var fullPhoneNumber = getParsedPhoneNumber();

    $.post("./customer/save", {name: customerName, number: fullPhoneNumber},
        function (saved) {
            saved ? location.reload() : alertError();
        });
}

function addIndicativeOption(value) {
    $("#indicativeSelection")
        .append($("<option></option>")
            .attr("value", value.indicative)
            .text(value.country + " - (" + value.indicative + ")"));
}

function setIndicativeOptions() {
    $.get("./phone/all", function (data) {
        $.each(data, function (key, value) {
            addIndicativeOption(value);
        })
    });
}

function addClickListenerForSubmission() {
    $("#submitCustomer").click(function () {
        submitCustomer();
    });
}

function appendCustomerToTable(value) {
    $('#customersBody')
        .append('<tr><td>' + value.name + '</td><td>' + value.phone + '</td></tr>');
}

function addCustomersToTable(data) {
    $.each(data, function (key, value) {
        appendCustomerToTable(value);
    })
}

function populateCustomers() {
    $.get("./customer/all", function (data) {
        addCustomersToTable(data);
    });
}

function clearTable() {
    $('#customersBody').empty();
}

function getResults() {
    var nameFilterValue = $("#filterName").val();
    var numberFilterValue = $("#filterNumber").val();

    $.get("./customer/all", {name: nameFilterValue, number: numberFilterValue},
        function (data) {
            clearTable();
            addCustomersToTable(data)
        });
}

function addListenerForFilters() {
    $("#filterName, #filterNumber").on('input', function () {
        getResults();
    })
}

function setInputValid(isValid) {
    var invalidClass = "is-invalid";
    var validClass = "is-valid";
    var numberInput = $("#numberInput");
    if (isValid) {
        numberInput.removeClass(invalidClass);
        if (!numberInput.hasClass(validClass))
            numberInput.addClass(validClass);
    } else {
        numberInput.removeClass(validClass);
        if (!numberInput.hasClass(invalidClass))
            numberInput.addClass(invalidClass);
    }
}

function setListenerForNumberValid() {
    $("#numberInput, #indicativeSelection").on('input', function () {
        var phoneNumber = getParsedPhoneNumber();

        $.get("./phone/validate/" + phoneNumber, function (isValid) {
            setInputValid(isValid);
        })

    })
}

$(document).ready(function () {
    addClickListenerForSubmission();
    setIndicativeOptions();
    populateCustomers();
    addListenerForFilters();
    setListenerForNumberValid();
});
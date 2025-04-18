$(function(){  // kjøres når dokumentet er ferdig lastet
    hentAlle();
});

function hentAlle() {
    $.get( "/hentAlle", function( biler ) {
        formaterData(biler);
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function formaterData(biler) {
    let ut = "<table class='table table-striped'><tr><th>Personnr</th><th>Navn</th><th>Adresse</th>" +
        "<th>Kjennetegn</th><th>Merke</th><th>Type</th><th></th><th></th></tr>";
    for (const bil of biler) {
        ut += "<tr><td>" + bil.personnr + "</td><td>" + bil.navn + "</td><td>" + bil.adresse + "</td>" +
            "<td>" + bil.kjennetegn + "</td><td>" + bil.merke + "</td><td>" + bil.type + "</td>" +
            "<td> <button class='btn btn-primary' onclick='idTilEndring("+bil.id+")'>Endre</button></td>"+
            "<td> <button class='btn btn-danger' onclick='slettEnMotorvogn("+bil.personnr+")'>Slett</button></td>"+
            "</tr>";
    }
    ut += "</table>";
    $("#bilene").html(ut);
}

function idTilEndring(id) {
    window.location.href = "/endre.html?"+id;
}

function slettEnMotorvogn(personnr) {
    const url = "/slettEnMotorvogn?personnr="+personnr;
    $.get( url, function() {
        window.location.href = "/";
    });
}

function slettAlle() {
    $.get( "/slettAlle", function() {
        hentAlle();
    });
}

//Opprettelse av logg ut
function loggUt(){
    //enkelt get tag
    $.get("/loggUt", function (){
        window.location.href = "/";
    });
}
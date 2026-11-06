function pruefePasswort() {

    let passwort = document.getElementById("password").value;
    let passwort2 = document.getElementById("password2").value;

    let fehlerPasswort = document.getElementById("passwortFehler");
    let fehlerBestaetigung = document.getElementById("bestaetigungFehler");

    fehlerPasswort.innerHTML = "";
    fehlerBestaetigung.innerHTML = "";

    if (passwort.length < 6) {
        fehlerPasswort.innerHTML =
            "Das Passwort muss mindestens 6 Zeichen enthalten.";
        return false;
    }

    if (passwort !== passwort2) {
        fehlerBestaetigung.innerHTML =
            "Die beiden Passwörter stimmen nicht überein.";
        return false;
    }

    return true;
}
function bestaetigeKasse() {

    return confirm(
        "Achtung!\n\n" +
        "Eine Stornierung der ausgewählten Tickets " +
        "ist nach dem Kauf nicht mehr möglich.\n\n" +
        "Möchtest du wirklich zur Kasse gehen?"
    );
}

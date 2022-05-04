# Ukeoppgave i sesjoner
Ta utgangspunkt i forrige ukes oppgave og legg til funksjonalitet for innlogging. Før man logger inn skal det bare være lov å se på motorvognregisteret, ikke legge til, endre eller slette. Det skal også være mulighet for å logge ut.

Opprett en egen tabell hvor bruker og passord lagres. Det er valgfritt om man ønsker å legge inn brukerene direkte med .sql-filer (ved oppstart av webapplikasjonen) eller om man ønsker å lage en egen registreringsløsning. Dersom en egen registreringsløsning lages kan det tenkes at det er en admin-bruker som har adgang til denne registreringsløsningen, eller at alle registrerte brukere også kan registrere nye brukere. Det første alternativet krever antageligvis en ny kolonne i tabellen for registrerte brukere.

Passordet i tabellen legges i klartekst (skal krypteres senere).
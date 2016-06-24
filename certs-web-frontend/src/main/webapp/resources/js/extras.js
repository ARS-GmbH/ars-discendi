/*
 * Fügt an jede Tabellen-Zeile einen Click-Handler ein, der dafür sorgt, dass zum hinterlegen
 * Link navigiert wird. Konfiguriert die Tooltips.
 */
$(document).ready(function(){
	var directTo = function() {
		window.location = $(this).data('href');
		return false;
	};
	$('table tr[data-href]').click(directTo);
	$('tr td[data-href]').click(directTo);
	$('[data-toggle="tooltip"]').tooltip()
});

/*
 * Konfiguriert zusätzliche Plugins.
 */
var pluginsConfig = function() {
	$(".chosen-select").chosen({width: "100%"});
	$('.datepicker').datepicker({format: "dd.mm.yyyy", weekStart: 1, language: "de"});
	// Setzt den Placeholder auf alle datepicker-Felder
	if ($('.datepicker').attr('disabled') !== 'disabled') {
		$('.datepicker').attr('placeholder', 'dd.MM.yyyy');
	}
}

$(document).ready(pluginsConfig);
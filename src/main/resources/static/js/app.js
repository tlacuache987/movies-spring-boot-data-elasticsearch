$(document).ready(function() {

	$("#searchString").val("&Baby");
	var prefix = "/api/movies";
	var service = "/searchByNameWithSpecialCharacters";
	
	var getGenres = function(arr){
		var s = "";
		arr.forEach(function(item, index){
			s += item.name +',';
		})
		return s;
	}

	$("#doSearch").on("click", function() {
		var i = 0;
		$("#moviesDiv").empty();
		$.ajax({
			url : prefix + service + '?name='+ escape($("#searchString").val()),
			method : 'GET',
			success : function(data) {
				
				console.log(data.movies)
				
				data.movies.forEach(function(m, i) {
					var div = $("<div>");
					
					console.log(m)
					div.append($("<span>").html("<b>"+(++i)+")</b>")).append("&nbsp;");
					div.append($("<span>").html("<b>Movie id:</b>"+m.id)).append("&nbsp;");
					div.append($("<span>").html("<b>Name:</b> "+m.name)).append("&nbsp;");
					div.append($("<span>").html("<b>Rating:</b> "+m.name)).append("&nbsp;");
					div.append($("<span>").html("<b>Genres:</b> "+getGenres(m.genre))).append("&nbsp;");
					$("#moviesDiv").append(div);
				});
				
				
			}
		});
	});
});
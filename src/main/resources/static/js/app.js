$(document).ready(function() {

	var prefix = "/services";
	var globers_microservice = "/globers-service";
	var workstations_microservice = "/workstations-service";

	$("#getGlobers").on("click", function() {
		var i = 0;
		$.ajax({
			url : prefix + globers_microservice + '/v1/globers',
			method : 'GET',
			success : function(globers) {
				globers.forEach(function(glober, index) {
					$.ajax({
						url : prefix + workstations_microservice + '/v1/workstations/'+glober.workstationPosition.idWorkstation,
						method : 'GET',
						success : function(workstation) {
							
							var div = $("<div>");
							
							console.log(workstation)
							
							div.append($("<span>").html("<b>Glober id:</b>"+glober.id)).append("&nbsp;");
							div.append($("<span>").html("<b>Name:</b> "+glober.name)).append("&nbsp;");
							div.append($("<span>").html("<b>Workstation Position:</b> Floor "+glober.workstationPosition.floor+", Desk: "+glober.workstationPosition.deskNumber)).append("&nbsp;");
							div.append($("<span>").html("<b>Workstation:</b> Vendor "+workstation.vendor+", Model: "+workstation.model+", Facilities Serial Number: "+workstation.facilitiesSerialNumber)).append("&nbsp;");
							$("#globers").append(div);
							
							i++;
							if(i==3)
								$("#globers").append($("<br>"));
						}
					});
					
				});
			}
		});
	});
});
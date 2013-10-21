$(document).ready(
		function() {
			$(".edit").click(function() {
				var currentId = $(this).attr('id');
				currentRowId = currentId.substr(4);
				var valueClass = "." + "table_value" + currentRowId;
				var edt_valClass = "." + "edit_input" + currentRowId;
				$(valueClass).hide();
				$(edt_valClass).show();
				$("#edit" + currentRowId).hide();
				$("#confirm" + currentRowId).show();
				$('#cncl' + currentRowId).show();
			});
			$(".cncl").click(function() {
				var currentId = $(this).attr('id');
				currentRowId = currentId.substr(4);
				var valueClass = "." + "table_value" + currentRowId;
				var edt_valClass = "." + "edit_input" + currentRowId;
				$(valueClass).show();
				$(edt_valClass).hide();
				$("#edit" + currentRowId).show();
				$("#confirm" + currentRowId).hide();
				$("#cncl" + currentRowId).hide();
			});
			
			$(".confirm").click(function() {
				var currentId = $(this).attr('id');
				var currentRowId = currentId.substr(7);
				$("#row_id").val(currentRowId);
			});
			
			$("#encodingSelect").change(function() {
				var currEncoding = $("#encodingSelect").val();
				$("#encodingId").val(currEncoding);
				$("#indexForm").submit();
			});

			$(".signSliding").hide();
			$(".show_hide").show();
			$('.show_hide').click(function() {
				$(".signSliding").slideToggle();
			});

			$(".filterValue").keypress(
					function(event) {
						if (event.which == 13) {	
							var lastRowId	= 		$('#filterTable >tbody >tr').length-1;						
							var selectId    = 		'filterField'.concat(lastRowId);						
							var inputId		=		'filterValue'.concat(lastRowId);
							var newRowId	=		lastRowId + 1;						
							var newSelectId =		'filterField'.concat(newRowId);
							var newValueId	=		'filterValue'.concat(newRowId);						
							var newSelect	=		$('#'.concat(selectId)).clone();
							var newInput	=		$('#'.concat(inputId)).clone();
							$("#filterTable").find('tbody')
							.append($('<tr>')
									.append($('<td>').append(newSelect))
									.append($('<td>').append(newInput)));
							alert(newSelect.innerHtml.toString());
//												var rowId = th
//												var oldSelect 
//												var newSelect  = 
//												var row=  $('<tr></tr>').append($('<td></td>').text(
//											   
//												$("#filterTable").find('tbody').append($('<tr>')
//																 .append($('<td>').append($('<input>').attr('type','text').attr('id', 'tst_id'))
//																 .append($('<td>').append($('<input>').attr('type','text').attr('id', 'tst_id1'))
//																 )));			
////											}
//								});		
							}
				});
});

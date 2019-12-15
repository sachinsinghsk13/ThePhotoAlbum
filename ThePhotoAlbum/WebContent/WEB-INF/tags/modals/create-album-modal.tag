<div class="modal fade" id="createAlbumModal" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="createAlbumModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="createAlbumModalLabel">Create Album</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">

				<div class="form-group">
					<input type="text" class="form-control" placeholder="Album Title"
						id="albumTitle">
				</div>
				<div class="form-group">
					<textarea id="albumDescription" cols="30" rows="3"
						class="form-control" style="resize: none;"
						placeholder="Album Description"></textarea>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-secondary btn-sm"
					data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-success btn-sm"
					id="albumCreateBtn">Create</button>
			</div>
		</div>
	</div>
</div>
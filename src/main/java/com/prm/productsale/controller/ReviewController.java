package com.prm.productsale.controller;

import com.prm.productsale.dto.request.ReviewReplyRequest;
import com.prm.productsale.dto.request.ReviewRequest;
import com.prm.productsale.dto.request.VoteRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.serivceimp.ReviewImp;
import com.prm.productsale.services.serivceimp.ReviewReplyImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/reviews")
@CrossOrigin
@Tag(name = "Review API", description = "API for Review")
public class ReviewController {

  @Autowired
  private ReviewImp reviewImp;
  @Autowired
  private ReviewReplyImp replyImp;

  @Operation(
          summary = "Get review list by productID",
          description = "ADMIN or MEMBER can get review list by productID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  )
          }
  )
  @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
  @GetMapping("/product/{productID}")
  public ResponseEntity<?> getByProductId(@PathVariable int productID) {
    BaseResponse response =
            new BaseResponse(200, "success", reviewImp.getByProductId(productID));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Create review ",
          description = "ADMIN or MEMBER can create review",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  )
          }
  )
  @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
  @PostMapping()
  public ResponseEntity<?> createReview(@RequestBody ReviewRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", reviewImp.createReview(request));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Delete own review",
          description = "MEMBER can delete their reviews",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  )
          }
  )
  @PreAuthorize("hasRole('MEMBER')")
  @DeleteMapping("/{reviewID}")
  public ResponseEntity<?> deleteOwnReview(@PathVariable int reviewID) {
    BaseResponse response =
            new BaseResponse(200, "success", "");
    reviewImp.deleteOwnReview(reviewID);
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Vote review",
          description = "MEMBER can vote reviews",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  )
          }
  )
  @PreAuthorize("hasRole('MEMBER')")
  @PostMapping("/review-vote")
  public ResponseEntity<?> vote(@RequestBody VoteRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", "");
    reviewImp.vote(request);
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Undo vote",
          description = "MEMBER can undo vote",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  )
          }
  )
  @PreAuthorize("hasRole('MEMBER')")
  @DeleteMapping("/review-vote/{reviewID}")
  public ResponseEntity<?> undoVote(@PathVariable int reviewID) {
    BaseResponse response =
            new BaseResponse(200, "success", "");
    reviewImp.undoVote(reviewID);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get replies for a review")
  @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
  @GetMapping("/{reviewId}/replies")
  public ResponseEntity<?> getReplies(@PathVariable int reviewId) {
    BaseResponse response =
            new BaseResponse(200, "success", replyImp.getRepliesByReviewId(reviewId));
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Create a reply for a review")
  @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
  @PostMapping("/{reviewId}/replies")
  public ResponseEntity<?> createReply(@PathVariable int reviewId,
                                       @RequestBody ReviewReplyRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", replyImp.createReply(reviewId, request));
    return ResponseEntity.ok(response);
  }
}


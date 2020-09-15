package com.problemfighter.pfspring.webtestmodule.rr;


import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.ResponseProcessor;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rr")
public class RequestResponseController implements RequestResponse {


    @RequestMapping(value = "/response-with-code", method = RequestMethod.GET)
    public MessageResponse responseWithCode() {
        return responseProcessor().response("Success Response", "1313");
    }

    @RequestMapping(value = "/response-message", method = RequestMethod.GET)
    public MessageResponse responseMessage() {
        return responseProcessor().response("Success Response");
    }

    @RequestMapping(value = "/response-error", method = RequestMethod.GET)
    public MessageResponse responseError() {
        return responseProcessor().error("Error Response");
    }

    @RequestMapping(value = "/response-error-with-code", method = RequestMethod.GET)
    public MessageResponse responseErrorWithCode() {
        return responseProcessor().error("Error Response", "3131");
    }

    @RequestMapping(value = "/unknown-error", method = RequestMethod.GET)
    public MessageResponse unknownError() {
        return ResponseProcessor.unknownError();
    }

    @RequestMapping(value = "/not-found", method = RequestMethod.GET)
    public MessageResponse notFound() {
        return ResponseProcessor.notFound();
    }

    @RequestMapping(value = "/bad-request", method = RequestMethod.GET)
    public MessageResponse badRequest() {
        return ResponseProcessor.badRequest();
    }

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public MessageResponse unauthorized() {
        return ResponseProcessor.unauthorized();
    }

    @RequestMapping(value = "/forbidden", method = RequestMethod.GET)
    public MessageResponse forbidden() {
        return ResponseProcessor.forbidden();
    }

    @RequestMapping(value = "/code-error", method = RequestMethod.GET)
    public MessageResponse codeError() {
        return ResponseProcessor.codeError();
    }

    @RequestMapping(value = "/validation-error", method = RequestMethod.GET)
    public MessageResponse validationError() {
        return ResponseProcessor.validationError();
    }

    @RequestMapping(value = "/error-message", method = RequestMethod.GET)
    public MessageResponse errorMessage() {
        return ResponseProcessor.errorMessage("Error Message");
    }

    @RequestMapping(value = "/success-message", method = RequestMethod.GET)
    public MessageResponse successMessage() {
        return ResponseProcessor.successMessage("Success Message");
    }


    @RequestMapping(value = "/other-error", method = RequestMethod.GET)
    public MessageResponse otherError() {
        return ResponseProcessor.otherError("Other Message");
    }

}

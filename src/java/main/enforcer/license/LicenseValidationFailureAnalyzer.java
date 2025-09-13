package com.gmail.bishoybasily.licensing.enforcer.license;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class LicenseValidationFailureAnalyzer extends AbstractFailureAnalyzer<LicenseValidationException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, LicenseValidationException cause) {
        return new FailureAnalysis(cause.getMessage(), "Couldn't verify the license, Please contact us to get a License", cause);
    }

}

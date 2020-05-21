package com.student.validations;

import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password == null || password.isEmpty()) {
        	return true;
        }
        
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // at least 5 characters
                new LengthRule(5, 30),

                // at least one upper-case character
               // new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
               // new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                //new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
             //   new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}

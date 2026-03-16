# SPRING BOOT LOGGING

## Logging Levels

- **TRACE**
- **DEBUG**
- **INFO**
- **WARN**
- **ERROR**
- **OFF**

## Logging Level preferences
- **TRACE → DEBUG → INFO → WARN → ERROR → OFF**

**TRACE** 
    
    It gives the each and every line of details. It is very rerely used one.

**DEBUG**

    It shows internal flow and variable values.

**INFO**
    
    It shows high-level logs, and it is configured as default one.

**WARN**
    
    It indicates the something might be wrong not failure.

**ERROR**
    
    Something went wrong

**OFF**
    
    Disables the logging levels in spring boot.

### If you want to set particular logging level you can use the spring boot property in applicaiton.propertis fle
    logging.level.root=DEBUG



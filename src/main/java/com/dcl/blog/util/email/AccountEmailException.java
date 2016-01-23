package com.dcl.blog.util.email;


/**
 * 自定义异常
 *
 */
public class AccountEmailException
    extends Exception
{
    private static final long serialVersionUID = -4817386460334501672L;

    public AccountEmailException( String message )
    {
        super( message );
    }

    public AccountEmailException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}

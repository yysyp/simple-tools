

Encountered a weired issue, when run UT one by one, all UT can pass, 
but when run all UT together, one UT will fail.

later on, found out the issue is that:
one of the UT has the static mock of "org.apache.commons.io.IOUtils"
but didn't close the static mock after used.
so the later UT which has the business code of using the "org.apache.commons.io.IOUtils" will fail.

Fix change the static mock 
From:
    MockedStatic<IOUtils> utilities = Mockito.mockStatic(IOUtils.class);
    Mockito.when(IOUtils.copy(any(InputStream.class), any(ServletOutputStream.class))).thenReturn(1);
    xxx
    //The issue is that there, we should have the utilities.close()

To:

    try(MockedStatic<IOUtils> utilities = Mockito.mockStatic(IOUtils.class);) {
    
    }

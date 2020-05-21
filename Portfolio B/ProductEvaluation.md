
# Product Evaluation

Throughout project development, a portion of evaluation methods were used to help in narrowing down the design to what our client would like the most. Investigations were carried out at each cycle in order to ensure that our development plan was meeting the requirements.
## Development Cycle

During the first meeting it became clear that the client did not have any specific idea of what he would like the final product to be. Therefore, we only extracted the end goals that the client had in mind.

After the meeting we have used double diamond framework to design a concentrated solution from the meeting. We brainstormed different ideas
and as the end result we decided a Web application was the best option and the client agreed with us.

Since we had set out our main requirements specification early in the process we found it to be of less importance to meet as regularly with our client. Most time was devoted to working on the text extraction and other back-end modules. The decision was to return to more regular meetings once we cover those essential aspects of the project.


## Beta Release Evaluation

As the product was in a working state we needed to test it on potential users to understand what kind of UI we needed to develop.

We arranged a meeting in the Bristol Museum, where we met with the digitalisation team and got more feedback on the project. We also decided to carry out informal interviews with our friends to gain more objective insights.

### User Testing:

The users were given a simple goal to use the web application to extract text from a photo stored in a specified folder. We watched them as they navigated the site and noted what was not intuitive or when the users were not sure what to do. The tests showed among other things:

- extraction takes some time and we need to provide a responsive feedback
- extraction should be available from the gallery
- the extracted text should have an option to be edited

Additionally, the client pointed out that the graphical interface should resemble the one from the official Bristol Museum website.

This feedback showed us on what we should focus next to improve the quality and usability of the product.

## Final Release Evaluation

Unfortunately, this last iteration of the product has been greatly influenced by the COVID-19 situation, which we explain in our COVID-19 Statement. The final evaluation could also not take place in a live, face to face interview. Therefore, we decided to run a heuristic evaluation, specifically Nielsen’s heuristics.

![Nielsen's Heuristics](images/Nielsen'sHeuristics.jpeg)

\
And here are some of the results of the evaluation:

| Description of issue &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | Heuristic(s) addressed | Frequency 0(rare) to 4(common) | Impact 0(easy) to 4(difficult) | Persistence 0(once) to 4(repeated) | Severity (F+I+P)/3 |
| :-----               |    :-------------:     | :-------: | :-------: | :-------: | --------: |
|  1. Design does not resemble the official Bristol Museum website. | 4,5,8 | 4 | 2 | 4 | 3.67 |   
|  2. Edit and delete options are missing from already submitted photos | 3,6,7 | 2 |  4 | 3 | 3 |
|  3. Unable to upload more than one photo at once | 3,7 | 2 | 2 | 2 | 2 |
|  4. Unofficial certificate causes browser warning | 6 | 4 | 1 | 1 | 2 |
